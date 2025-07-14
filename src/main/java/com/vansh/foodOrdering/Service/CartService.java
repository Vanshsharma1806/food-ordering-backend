package com.vansh.foodOrdering.Service;

import com.vansh.foodOrdering.Model.Cart;
import com.vansh.foodOrdering.Model.CartItem;
import com.vansh.foodOrdering.Repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    public Cart getCart(String userId){
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if(cart == null){
            cart = new Cart();
            cart.setUserId(userId);
            cart.setItemList(new ArrayList<>());
            cart.setTotalPrice(0);
        }
        return cart;
    }

    public Cart addItemToCart(String userId, CartItem newCarItem){
        boolean found = false;
        Cart cart = getCart(userId);
        List<CartItem> itemList = cart.getItemList();
        for(CartItem item : itemList){
            if(item.getItemId().equals(newCarItem.getItemId())){
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }
        if(!found){
            itemList.add(newCarItem);
        }
        cart.setItemList(itemList);
        cart.setTotalPrice(calculateTotal(itemList));
        cartRepository.save(cart);
        return cart;
    }

    public Cart removeItem(String userId, String itemId){
        Cart cart = getCart(userId);
        List<CartItem> itemList = cart.getItemList();


        for(int i=0;i<itemList.size();i++){
            CartItem item = itemList.get(i);
            if(item.getItemId().equals(itemId)){
                item.setQuantity(item.getQuantity() - 1);
                if(item.getQuantity() == 0){
                    itemList.remove(i);
                }
            }
        }
        cart.setTotalPrice(calculateTotal(itemList));
        cart.setItemList(itemList);
        cartRepository.save(cart);
        return cart;
    }

    public Cart clearCart(String userId){
        Cart cart = getCart(userId);
        cart.setItemList(new ArrayList<>());
        cart.setTotalPrice(0);
        cartRepository.save(cart);
        return cart;
    }

    public int calculateTotal(List<CartItem> items) {
        int total = 0;
        for (CartItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
