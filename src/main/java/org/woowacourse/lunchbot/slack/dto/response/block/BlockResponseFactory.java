package org.woowacourse.lunchbot.slack.dto.response.block;

import org.woowacourse.lunchbot.domain.Restaurant;
import org.woowacourse.lunchbot.slack.fragment.block.ActionsBlock;
import org.woowacourse.lunchbot.slack.fragment.block.Block;
import org.woowacourse.lunchbot.slack.fragment.block.SectionBlock;
import org.woowacourse.lunchbot.slack.fragment.composition.text.MrkdwnText;
import org.woowacourse.lunchbot.slack.fragment.composition.text.PlainText;
import org.woowacourse.lunchbot.slack.fragment.composition.text.Text;
import org.woowacourse.lunchbot.slack.fragment.element.ButtonElement;
import org.woowacourse.lunchbot.slack.fragment.element.Element;
import org.woowacourse.lunchbot.slack.fragment.element.ImageElement;

import java.util.Arrays;
import java.util.List;

public class BlockResponseFactory {

    public static Block createRestaurantBlock(Restaurant restaurant) {
        Text text = MrkdwnText.createRestuarantTextFrom(restaurant);
        Element element = new ImageElement(restaurant.getImageUrl(), restaurant.getName());
        return new SectionBlock(text, element);
    }

    public static Block createRestaurantBlockWithoutImage(Restaurant restaurant) {
        Text text = MrkdwnText.createRestuarantTextFrom(restaurant);
        return new SectionBlock(text);
    }

    public static Block createRecommendBlock(String blockId) {
        List<Element> elements = Arrays.asList(
                new ButtonElement(new PlainText(":fortune_cookie: 오늘 뭐 먹지?"), "recommend")
        );

        return new ActionsBlock(blockId, elements);
    }

    public static Block createInitActionBlock(String blockId) {
        List<Element> elements = Arrays.asList(
                new ButtonElement(new PlainText(":rice: 한식"), "korean"),
                new ButtonElement(new PlainText(":dumpling: 중식"), "chinese"),
                new ButtonElement(new PlainText(":oden: 분식"), "snack"),
                new ButtonElement(new PlainText(":cut_of_meat: 양식"), "western"),
                new ButtonElement(new PlainText(":sushi: 일식"), "japanese"),
                new ButtonElement(new PlainText(":knife_fork_plate: 기타"), "etc")
        );

        return new ActionsBlock(blockId, elements);
    }

    public static Block createTogetherActionBlock(String blockId) {
        List<Element> elements = Arrays.asList(
                new ButtonElement(new PlainText(":handshake: 같이 먹어요"), "apply"),
                new ButtonElement(new PlainText(":ok_hand: 결과 확인"), "result")
        );

        return new ActionsBlock(blockId, elements);
    }
}
