package com.vansh.foodOrdering.Repository;

import com.vansh.foodOrdering.Model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
    Optional<Order> findByIdAndUserId(String id, String userId);
    List<Order> findAll();
    Order findTopByUserIdOrderByCreatedAtDesc(String userId);

}
