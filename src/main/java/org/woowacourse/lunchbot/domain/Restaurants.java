package org.woowacourse.lunchbot.domain;

import org.springframework.stereotype.Component;
import org.sqlite.core.DB;
import org.woowacourse.lunchbot.slack.RestaurantType;
import org.woowacourse.lunchbot.slack.fragment.block.ActionsBlock;
import org.woowacourse.lunchbot.slack.fragment.block.Block;
import org.woowacourse.lunchbot.slack.fragment.block.SectionBlock;
import org.woowacourse.lunchbot.slack.fragment.composition.text.MrkdwnText;
import org.woowacourse.lunchbot.slack.fragment.composition.text.PlainText;
import org.woowacourse.lunchbot.slack.fragment.element.ButtonElement;
import org.woowacourse.lunchbot.slack.fragment.element.ImageElement;

import java.awt.*;
import java.io.IOException;
import java.nio.channels.MembershipKey;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Component
public class Restaurants {

    private Map<RestaurantType, List<Block>> restaurantTypeMatcher = new HashMap<>();

    public Restaurants(GSpreadFetcher gSpreadFetcher) throws GeneralSecurityException, IOException, SQLException {
        /* 한 번만 실행하고 지우기 */
        DBConnector dbConnector = new DBConnector();
        dbConnector.createRestaurnatTable();
        dbConnector.initRestaurantData();
        /* 여기까지 */
        List<Restaurant> restaurants = dbConnector.createRestaurant();
        System.out.println("list length : " +restaurants.size());

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
