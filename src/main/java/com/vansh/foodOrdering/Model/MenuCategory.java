package com.vansh.foodOrdering.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;


@Data
@AllArgsConstructor
public class MenuCategory {
    private String title;
    private List<MenuItem> items;
}
