package org.woowacourse.lunchbot.service;

import org.springframework.stereotype.Service;
import org.woowacourse.lunchbot.domain.Restaurant;
import org.woowacourse.lunchbot.domain.Restaurants;
import org.woowacourse.lunchbot.slack.RestaurantType;

import java.util.List;

@Service
public class RestaurantService {

    private final Restaurants restaurants;

    public RestaurantService(Restaurants restaurants) {
        this.restaurants = restaurants;
    }

    public List<Restaurant> findByType(RestaurantType restaurantType) {
        return restaurants.get(restaurantType);
    }
}
