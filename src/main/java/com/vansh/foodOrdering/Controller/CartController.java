package com.vansh.foodOrdering.Controller;

import com.vansh.foodOrdering.Model.Cart;
import com.vansh.foodOrdering.Model.CartItem;
import com.vansh.foodOrdering.Service.CartService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    public String getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/get-cart")
    public ResponseEntity<?> getCart(){
        String userId = getUserId();
        Cart cart =cartService.getCart(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/add-item")
    public ResponseEntity<?> addItem(@RequestBody CartItem item){
        String userId = getUserId();
        Cart cart = cartService.addItemToCart(userId, item);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove-item/{itemId}")
    public ResponseEntity<?> removeItem(@PathVariable("itemId") String itemId){
        String userId = getUserId();
        Cart cart = cartService.removeItem(userId, itemId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("clear-cart")
    public ResponseEntity<?> clearCart(){
        String userId = getUserId();
        Cart cart = cartService.clearCart(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

}
