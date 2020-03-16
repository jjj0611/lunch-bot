package org.woowacourse.lunchbot.domain;

import org.springframework.stereotype.Component;
import org.woowacourse.lunchbot.slack.RestaurantType;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Restaurants {
    private static final int RECOMMEND_SIZE = 5;
    private final GSpreadFetcher gSpreadFetcher;
    private List<Restaurant> recommends;
    private Map<RestaurantType, List<Restaurant>> restaurantTypeMatcher = new HashMap<>();

    public Restaurants(GSpreadFetcher gSpreadFetcher) throws GeneralSecurityException, IOException {
        this.gSpreadFetcher = gSpreadFetcher;
        initializeRestaurants();
    }

    public List<Restaurant> get(RestaurantType restaurantType) {
        return restaurantTypeMatcher.get(restaurantType);
    }

    public List<Restaurant> getRecommendsOfRandom() {
        return recommends;
    }

    public void initializeRestaurants() {
        restaurantTypeMatcher = new HashMap<>();
        for (RestaurantType restaurantType : RestaurantType.values()) {
            restaurantTypeMatcher.put(restaurantType, new ArrayList<>());
        }

        List<Restaurant> restaurants = null;
        try {
            restaurants = gSpreadFetcher.fetchRestaurants();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Restaurant restaurant : restaurants) {
            RestaurantType restaurantType = restaurant.getType();
            restaurantTypeMatcher.get(restaurantType).add(restaurant);
        }

        Collections.shuffle(restaurants);
        recommends = restaurants.subList(0, RECOMMEND_SIZE);
    }
}
