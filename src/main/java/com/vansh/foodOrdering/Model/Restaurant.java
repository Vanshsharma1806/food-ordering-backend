package com.vansh.foodOrdering.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Restaurant {
    private String id;
    private String name;
    private double avgRating;
    private List<String> cuisines;
    private String imageId;
    private String areaName;
    private String avgDeliveryTime;
    private String discountHeader;
    private String discountSubHeader;
    private boolean pureVeg;
}
