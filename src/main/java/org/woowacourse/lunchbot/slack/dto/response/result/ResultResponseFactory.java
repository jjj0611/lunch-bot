package org.woowacourse.lunchbot.slack.dto.response.result;

import org.woowacourse.lunchbot.slack.RestaurantType;
import org.woowacourse.lunchbot.slack.dto.response.common.ModalResponse;
import org.woowacourse.lunchbot.slack.dto.response.common.ModalSubmissionType;
import org.woowacourse.lunchbot.slack.fragment.block.Block;
import org.woowacourse.lunchbot.slack.fragment.composition.text.PlainText;
import org.woowacourse.lunchbot.slack.fragment.view.ModalView;

import java.util.List;

public class ResultResponseFactory {

    public static ModalResponse of(String triggerId, RestaurantType restaurantType, List<Block> restaurants) {
        ModalView modalView = new ModalView(
                ModalSubmissionType.of(restaurantType.getModalSubmissionType()),
                new PlainText(restaurantType.getTitle()),
                new PlainText("취소"),
                restaurants
        );

        return new ModalResponse(triggerId, modalView);
    }

}
