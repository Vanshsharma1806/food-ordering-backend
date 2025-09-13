package com.vansh.foodOrdering.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.vansh.foodOrdering.Model.CartItem;
import com.vansh.foodOrdering.Model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    private final OrderService orderService;

    public StripeService(OrderService orderService) {
        this.orderService = orderService;
    }

    public String getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public Map<String, Object> createCheckoutSession(Order order) throws Exception{

        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:1234/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:1234/cancel?session_id={CHECKOUT_SESSION_ID}");

        for(CartItem item : order.getItems()){
            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams.LineItem.builder()
                    .setQuantity((long) item.getQuantity())
                    .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("inr")
                                    .setUnitAmount((long) item.getPrice())
                                    .setProductData(
                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                    .setName(item.getName())
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();
            sessionBuilder.addLineItem(lineItem);
        }
        SessionCreateParams sessionCreateParams = sessionBuilder.build();

        Session session = Session.create(sessionCreateParams);
        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", session.getId());

        return response;
    }

    public Order confirmPayment(String sessionId, Order order) throws Exception {
        Stripe.apiKey = stripeSecretKey;

        Session session = Session.retrieve(sessionId);

        if(! "paid".equals(session.getPaymentStatus())){
            throw new RuntimeException("Payment not completed yet");
        }

        String userId = getUserId();
        return orderService.placeOrder(order, userId);
    }
}
