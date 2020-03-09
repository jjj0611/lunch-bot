package org.woowacourse.lunchbot.slack.dto.response.result;

import org.woowacourse.lunchbot.domain.Restaurant;
import org.woowacourse.lunchbot.slack.fragment.block.Block;
import org.woowacourse.lunchbot.slack.fragment.block.SectionBlock;
import org.woowacourse.lunchbot.slack.fragment.composition.text.MrkdwnText;
import org.woowacourse.lunchbot.slack.fragment.composition.text.Text;
import org.woowacourse.lunchbot.slack.fragment.element.Element;
import org.woowacourse.lunchbot.slack.fragment.element.ImageElement;

public class BlockResponseFactory {

    public static Block createRestaurantBlock(Restaurant restaurant) {
        Text text = MrkdwnText.createRestuarantTextFrom(restaurant);
        Element element = new ImageElement(restaurant.getImageUrl(), restaurant.getName());
        return new SectionBlock(text, element);
    }
}
