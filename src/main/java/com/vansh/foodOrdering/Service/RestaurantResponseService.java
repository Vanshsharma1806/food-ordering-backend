package com.vansh.foodOrdering.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vansh.foodOrdering.Model.MenuCategory;
import com.vansh.foodOrdering.Model.MenuItem;
import com.vansh.foodOrdering.Model.ResMenuResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantResponseService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String resResponseApi = "https://www.swiggy.com/dapi/menu/pl?page-type=REGULAR_MENU&complete-menu=true&lat=28.9711023&lng=77.6544715&restaurantId=";

    public ResMenuResponse fetchResMenuResponse(String resId){
        try{
            String MENU_API = resResponseApi + resId;
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");
            headers.set("Accept", "application/json");
            headers.set("Referer", "https://www.swiggy.com/");
            headers.set("Origin", "https://www.swiggy.com");

            HttpEntity<String> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(MENU_API, HttpMethod.GET, httpEntity, String.class);

            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode data = root.path("data");

//          basic info
            JsonNode info = data.path("cards").get(2).path("card").path("card").path("info");
            String id = info.path("id").asText();
            String name = info.path("name").asText();
            String city = info.path("city").asText();
            String locality = info.path("locality").asText();
            List<String> cuisines = new ArrayList<>();
            for(JsonNode c : info.path("cuisines")) cuisines.add(c.asText());
            String costForTwoMessage = info.path("costForTwoMessage").asText();
            double avgRating = info.path("avgRating").asDouble();
            String slaString = info.path("sla").path("slaString").asText();

//          categories
            List<MenuCategory> categories = new ArrayList<>();
            JsonNode cards = data.path("cards").get(4).path("groupedCard").path("cardGroupMap").path("REGULAR").path("cards");
            for(JsonNode card : cards){
                JsonNode categoryCard = card.path("card").path("card");
                String type = categoryCard.path("@type").asText();
                if(!"type.googleapis.com/swiggy.presentation.food.v2.ItemCategory".equals(type)) continue;

                String title = categoryCard.path("title").asText();
                List<MenuItem> items = new ArrayList<>();
                for(JsonNode itemCard : categoryCard.path("itemCards")){
                    JsonNode itemInfo = itemCard.path("card").path("info");
                    int price = itemInfo.has("price") ? itemInfo.path("price").asInt(): itemInfo.path("defaultPrice").asInt();
                    MenuItem item = new MenuItem(
                            itemInfo.path("id").asText(),
                            itemInfo.path("name").asText(),
                            price,
                            itemInfo.path("imageId").asText()
                    );
                    items.add(item);
                }
                categories.add(new MenuCategory(title, items));

            }
            return new ResMenuResponse(
                    id,
                    name,
                    city,
                    locality,
                    cuisines,
                    costForTwoMessage,
                    avgRating,
                    slaString,
                    categories
            );
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
