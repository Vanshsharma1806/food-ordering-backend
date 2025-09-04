package com.vansh.foodOrdering.Service;

import com.vansh.foodOrdering.Model.Order;
import com.vansh.foodOrdering.Repository.OrderRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderStatusScheduler {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderStatusScheduler(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }


    @Scheduled(fixedRate = 10000)
    public void updateOrderStatus() {
        Instant now = Instant.now();
//        System.out.println("scheduler running at " + now);

        List<Order> filteredOrders = orderRepository.findAll().stream()
                .filter(order -> order.getNextStatusUpdate() != null && !order.getNextStatusUpdate().isAfter(now))
                .toList();

//        System.out.println("Filtered Orders: " + filteredOrders);

        for (Order order : filteredOrders) {
            switch (order.getStatus()) {
                case "PLACED":
                    order.setStatus("PREPARING");
                    order.setNextStatusUpdate(now.plusSeconds(120));
                    break;

                case "PREPARING":
                    order.setStatus("OUT_FOR_DELIVERY");
                    order.setNextStatusUpdate(now.plusSeconds(300));
                    break;

                case "OUT_FOR_DELIVERY":
                    order.setStatus("DELIVERED");
                    order.setNextStatusUpdate(null);
                    break;
            }
            orderRepository.save(order);
        }
    }

}
