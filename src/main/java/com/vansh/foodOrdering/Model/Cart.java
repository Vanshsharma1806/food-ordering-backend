package com.vansh.foodOrdering.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Cart")
public class Cart {
    @Id
    private String cartId;
    private String userId;
    private List<CartItem> itemList;
    private int totalPrice;
}
