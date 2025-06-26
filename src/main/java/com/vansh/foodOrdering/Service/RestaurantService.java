package com.vansh.foodOrdering.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vansh.foodOrdering.Model.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RestaurantService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String SWIGGY_URL =
            "https://www.swiggy.com/dapi/restaurants/list/v5?lat=28.9711023&lng=77.6544715&is-seo-homepage-enabled=true&page_type=DESKTOP_WEB_LISTING";

    public List<Restaurant> fetchRestaurants(){
        List<Restaurant> restaurants = new ArrayList<>();
        try{
            HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
            headers.set("Accept", "application/json");
            headers.set("Referer", "https://www.swiggy.com/");
            headers.set("Origin", "https://www.swiggy.com");

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(SWIGGY_URL, HttpMethod.GET, entity, String.class);

            JsonNode root;
            try {
                root = objectMapper.readTree(response.getBody());

            }catch (JsonProcessingException e) {
                e.printStackTrace();
                return restaurants;
            }
            JsonNode cards = root.path("data").path("cards");
            JsonNode restaurantList = null;
            for(JsonNode card : cards){
                JsonNode tryRestaurants = card
                        .path("card")
                        .path("card")
                        .path("gridElements")
                        .path("infoWithStyle")
                        .path("restaurants");
                if(tryRestaurants.isArray()) {
                    restaurantList = tryRestaurants;
                    break;
                }
            }
            if(restaurantList != null){
                for(JsonNode restaurantNode : restaurantList) {
                    JsonNode info = restaurantNode.path("info");
                    String id = info.path("id").asText();
                    String name = info.path("name").asText();
                    double avgRating = info.path("avgRating").asDouble();
                    List<String> cuisines = new ArrayList<>();
                    for (JsonNode c : info.path("cuisines")) {
                        cuisines.add(c.asText());
                    }
                    String imageId = info.path("cloudinaryImageId").asText();
                    String areaName = info.path("areaName").asText();
                    String avgDeliveryTime = info.path("sla").path("slaString").asText();
                    String discountHeader = info.path("aggregatedDiscountInfoV3").path("header").asText();
                    String discountSubHeader = info.path("aggregatedDiscountInfoV3").path("subHeader").asText();
                    boolean pureVeg = info.has("veg") && info.path("veg").asBoolean();

                    restaurants.add(new Restaurant(id, name, avgRating, cuisines, imageId, areaName, avgDeliveryTime, discountHeader, discountSubHeader, pureVeg));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return restaurants;
    }


}
