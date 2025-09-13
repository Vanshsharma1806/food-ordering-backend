package com.vansh.foodOrdering.Controller;

import com.vansh.foodOrdering.Model.Order;
import com.vansh.foodOrdering.Service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> createCheckoutSession(@RequestBody Order order) throws Exception {
        Map<String, Object> checkoutSession = stripeService.createCheckoutSession(order);
        return new ResponseEntity<>(checkoutSession, HttpStatus.OK);
    }

    @PostMapping("/confirm")
    public Order confirmPayment(@RequestParam("sessionId") String sessionId, @RequestBody Order order) throws Exception {
        return stripeService.confirmPayment(sessionId, order);
    }
}
