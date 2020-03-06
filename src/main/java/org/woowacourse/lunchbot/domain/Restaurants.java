package org.woowacourse.lunchbot.domain;

import org.springframework.stereotype.Component;
import org.woowacourse.lunchbot.slack.RestaurantType;
import org.woowacourse.lunchbot.slack.fragment.block.Block;
import org.woowacourse.lunchbot.slack.fragment.block.SectionBlock;
import org.woowacourse.lunchbot.slack.fragment.composition.text.MrkdwnText;
import org.woowacourse.lunchbot.slack.fragment.element.ImageElement;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Restaurants {

    private Map<RestaurantType, List<Block>> restaurantTypeMatcher = new HashMap<>();

    public Restaurants(GSpreadFetcher gSpreadFetcher) throws GeneralSecurityException, IOException {
        List<Restaurant> restaurants = gSpreadFetcher.getData();
        Arrays.stream(RestaurantType.values())
                .forEach(restaurantType -> restaurantTypeMatcher.put(restaurantType, new ArrayList<>()));

        for (Restaurant restaurant : restaurants) {
            RestaurantType restaurantType = RestaurantType.createFromTitle(restaurant.getType());
            Block sectionBlock = new SectionBlock(MrkdwnText.createRestuarantTextFrom(restaurant),
                    new ImageElement(restaurant.getImageUrl(), restaurant.getName()));
            restaurantTypeMatcher.get(restaurantType).add(sectionBlock);
        }

        Collections.shuffle(restaurants);
        List<Block> recommends = restaurants.subList(0, 4).stream().map(restaurant ->
                new SectionBlock(MrkdwnText.createRestuarantTextFrom(restaurant),
                        new ImageElement(restaurant.getImageUrl(), restaurant.getName()))).collect(Collectors.toList());
        restaurantTypeMatcher.replace(RestaurantType.RECOMMEND, recommends);
    }

    public List<Block> get(RestaurantType restaurantType) {
        return restaurantTypeMatcher.get(restaurantType);
    }
}
