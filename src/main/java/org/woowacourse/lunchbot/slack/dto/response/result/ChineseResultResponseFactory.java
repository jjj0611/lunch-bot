package org.woowacourse.lunchbot.slack.dto.response.result;

import org.woowacourse.lunchbot.slack.dto.response.common.ModalResponse;
import org.woowacourse.lunchbot.slack.dto.response.common.ModalSubmissionType;
import org.woowacourse.lunchbot.slack.fragment.block.SectionBlock;
import org.woowacourse.lunchbot.slack.fragment.composition.text.MrkdwnText;
import org.woowacourse.lunchbot.slack.fragment.composition.text.PlainText;
import org.woowacourse.lunchbot.slack.fragment.view.ModalView;

import java.util.Arrays;

public class ChineseResultResponseFactory {

    public static ModalResponse of(String triggerId) {

        ModalView modalView = new ModalView(
                ModalSubmissionType.RECOMMAND_RESULT,
                new PlainText("중식"),
                new PlainText("취소"),
                Arrays.asList(
                        new SectionBlock(new MrkdwnText("*중식1*")),
                        new SectionBlock(new MrkdwnText("*중식2*"))
                )
        );

        return new ModalResponse(triggerId, modalView);
    }

}
