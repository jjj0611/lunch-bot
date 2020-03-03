package org.woowacourse.lunchbot.slack.fragment.composition.text;

import org.woowacourse.lunchbot.domain.Restaurant;

public class MrkdwnText extends Text {

    public static final String FORMATTED_RESTAURANT_STATEMENT = "*<%s|%s>*\n 대표 메뉴\n %s %d";

    public MrkdwnText(String text) {
        super(TextType.MRKDWN, text);
    }

    public static MrkdwnText createRestuarantTextFrom(Restaurant restaurant) {
        String restaurantStatement = String.format(
                FORMATTED_RESTAURANT_STATEMENT,
                restaurant.getUrl(), restaurant.getName(), restaurant.getMainMenu(), restaurant.getPrice()
        );
        return new MrkdwnText(restaurantStatement);
    }
}

