package org.woowacourse.lunchbot.slack.dto.response.result;

import org.woowacourse.lunchbot.slack.dto.response.common.ModalResponse;
import org.woowacourse.lunchbot.slack.dto.response.common.ModalSubmissionType;
import org.woowacourse.lunchbot.slack.fragment.block.SectionBlock;
import org.woowacourse.lunchbot.slack.fragment.composition.text.MrkdwnText;
import org.woowacourse.lunchbot.slack.fragment.composition.text.PlainText;
import org.woowacourse.lunchbot.slack.fragment.view.ModalView;

import java.util.Arrays;

public class RecommandResultResponseFactory {

    public static ModalResponse of(String triggerId) {

        ModalView modalView = new ModalView(
                ModalSubmissionType.RECOMMAND_RESULT,
                new PlainText("오늘의 추천"),
                new PlainText("취소"),
                Arrays.asList(
                        new SectionBlock(new MrkdwnText("*추천*")),
                        new SectionBlock(new MrkdwnText("*헤헤*"))
                )
        );

        return new ModalResponse(triggerId, modalView);
    }

}
