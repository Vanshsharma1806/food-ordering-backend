package com.vansh.foodOrdering.Controller;

import com.vansh.foodOrdering.Model.ResMenuResponse;
import com.vansh.foodOrdering.Service.RestaurantResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResMenuController {
    private final RestaurantResponseService responseService;

    public ResMenuController(RestaurantResponseService responseService) {
        this.responseService = responseService;
    }


    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> getRestaurantMenu(@PathVariable String id){
        ResMenuResponse response = responseService.fetchResMenuResponse(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
