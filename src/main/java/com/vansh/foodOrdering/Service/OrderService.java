package com.vansh.foodOrdering.Service;

import com.vansh.foodOrdering.Model.Order;
import com.vansh.foodOrdering.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(Order order, String userId){
        order.setUserId(userId);
        order.setStatus("PLACED");
        order.setNextStatusUpdate(Instant.now().plusSeconds(30));
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(String orderId, String status){
        Optional<Order> optOrder = orderRepository.findById(orderId);
        if(optOrder.isPresent()){
            Order order = optOrder.get();
            order.setStatus(status);
            order.setNextStatusUpdate(null);
            return orderRepository.save(order);
        }
        return null;
    }

    public List<Order> getOrdersByUserId(String userId){
        return orderRepository.findByUserId(userId);
    }

    public Order getOrderById(String id){
        return orderRepository.findById(id).orElse(null);
    }

}
