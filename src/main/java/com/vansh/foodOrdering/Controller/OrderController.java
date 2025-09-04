package com.vansh.foodOrdering.Controller;

import com.vansh.foodOrdering.Model.Order;
import com.vansh.foodOrdering.Service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public String getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Order order){

         Order savedOrder = orderService.placeOrder(order, getUserId());
         return new ResponseEntity<>(savedOrder, HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/status/{status}")
    public ResponseEntity<Order> updateStatus(@PathVariable String orderId, @PathVariable String status){
        Order order = orderService.updateOrderStatus(orderId, status);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getAllOrders(){

        List<Order> orders = orderService.getOrdersByUserId(getUserId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId){
        Order order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
