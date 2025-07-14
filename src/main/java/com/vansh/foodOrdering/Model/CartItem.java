package com.vansh.foodOrdering.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {
    private String itemId;
    private String name;
    private int price;
    private int quantity;
    private String imageId;
}