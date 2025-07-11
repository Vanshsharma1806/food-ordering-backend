package com.vansh.foodOrdering.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuItem {
    private String id;
    private String name;
    private int price;
    private String imageId;
}
