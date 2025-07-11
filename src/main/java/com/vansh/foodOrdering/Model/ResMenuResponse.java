package com.vansh.foodOrdering.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResMenuResponse {
    private String id;
    private String name;
    private String city;
    private String locality;
    private List<String> cuisines;
    private String costForTwoMsg;
    private double avgRating;
    private String slaString;
    private List<MenuCategory> categories;
}
