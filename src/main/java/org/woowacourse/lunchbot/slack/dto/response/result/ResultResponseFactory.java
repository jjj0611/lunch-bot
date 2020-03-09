package org.woowacourse.lunchbot.slack.dto.response.result;

import org.woowacourse.lunchbot.domain.Restaurant;
import org.woowacourse.lunchbot.slack.RestaurantType;
import org.woowacourse.lunchbot.slack.dto.response.block.BlockResponseFactory;
import org.woowacourse.lunchbot.slack.dto.response.common.ModalResponse;
import org.woowacourse.lunchbot.slack.dto.response.common.ModalSubmissionType;
import org.woowacourse.lunchbot.slack.fragment.composition.text.PlainText;
import org.woowacourse.lunchbot.slack.fragment.view.ModalView;

import java.util.List;
import java.util.stream.Collectors;

public class ResultResponseFactory {

    public static ModalResponse ofRandom(String triggerId, List<Restaurant> restaurants) {
        ModalView modalView = new ModalView(
                ModalSubmissionType.RECOMMEND_RESULT,
                new PlainText("오늘의 추천"),
                new PlainText("취소"),
                restaurants.stream()
                        .map(BlockResponseFactory::createRestaurantBlock)
                        .collect(Collectors.toList())
        );

        return new ModalResponse(triggerId, modalView);
    }

    public static ModalResponse of(String triggerId, RestaurantType restaurantType, List<Restaurant> restaurants) {
        ModalView modalView = new ModalView(
                ModalSubmissionType.of(restaurantType.getModalSubmissionType()),
                new PlainText(restaurantType.getTitle()),
                new PlainText("취소"),
                restaurants.stream()
                        .map(BlockResponseFactory::createRestaurantBlockWithoutImage)
                        .collect(Collectors.toList())
        );

        return new ModalResponse(triggerId, modalView);
    }

}
