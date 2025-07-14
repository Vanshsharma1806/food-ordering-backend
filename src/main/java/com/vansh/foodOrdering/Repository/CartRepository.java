package com.vansh.foodOrdering.Repository;

import com.vansh.foodOrdering.Model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {

    Optional<Cart> findByUserId(String s);
}
