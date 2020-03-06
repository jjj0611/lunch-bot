package org.woowacourse.lunchbot.domain;

import java.util.List;

public class RestaurantDto {

    List row;

    public RestaurantDto(List row) {
        this.row = row;
    }

    public Restaurant convertToRestaurant() {
        Long id = Long.parseLong(convertToString(row.get(0)));
        String name = convertToString(row.get(1));
        String type = convertToString(row.get(2));
        String mainMenu = convertToString(row.get(3));
        int price = Integer.parseInt(convertToString(row.get(4)));
        String url = convertToString(row.get(5));
        String imageUrl = convertToString(row.get(6));
        return new Restaurant(id, name, type, mainMenu, price, url, imageUrl);
    }

    private String convertToString(Object object) {
        return object.toString().trim();
    }

}
