package org.woowacourse.lunchbot.domain;

import lombok.Getter;
import org.woowacourse.lunchbot.slack.RestaurantType;

@Getter
public class Restaurant {

    private long id;
    private String name;
    private RestaurantType type;
    private String mainMenu;
    private int price;
    private String url;
    private String imageUrl;

    public Restaurant(
            long id,
            String name,
            RestaurantType type,
            String mainMenu,
            int price,
            String url,
            String imageUrl
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.mainMenu = mainMenu;
        this.price = price;
        this.url = url;
        this.imageUrl = imageUrl;
    }
}
