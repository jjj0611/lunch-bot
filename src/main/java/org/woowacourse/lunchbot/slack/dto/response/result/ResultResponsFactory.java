package org.woowacourse.lunchbot.slack.dto.response.result;

import org.woowacourse.lunchbot.slack.RestaurantType;
import org.woowacourse.lunchbot.slack.dto.response.common.ModalResponse;
import org.woowacourse.lunchbot.slack.dto.response.common.ModalSubmissionType;
import org.woowacourse.lunchbot.slack.fragment.block.SectionBlock;
import org.woowacourse.lunchbot.slack.fragment.composition.text.MrkdwnText;
import org.woowacourse.lunchbot.slack.fragment.composition.text.PlainText;
import org.woowacourse.lunchbot.slack.fragment.view.ModalView;

import java.util.Arrays;

public class ResultResponsFactory {

    public static ModalResponse of(String triggerId, RestaurantType restaurantType) {

        ModalView modalView = new ModalView(
                ModalSubmissionType.of(restaurantType.getModalSubmissionType()),
                new PlainText(restaurantType.getTitle()),
                new PlainText("취소"),
                Arrays.asList(
                        new SectionBlock(new MrkdwnText("*메뉴1*")),
                        new SectionBlock(new MrkdwnText("*메뉴2*"))
                )
        );

        return new ModalResponse(triggerId, modalView);
    }

}
