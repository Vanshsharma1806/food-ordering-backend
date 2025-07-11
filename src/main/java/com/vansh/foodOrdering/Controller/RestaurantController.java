package com.vansh.foodOrdering.Controller;

import com.vansh.foodOrdering.Model.Restaurant;
import com.vansh.foodOrdering.Service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins = "*")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRestaurants(){
        return ResponseEntity.ok(restaurantService.fetchRestaurants());
    }
}
