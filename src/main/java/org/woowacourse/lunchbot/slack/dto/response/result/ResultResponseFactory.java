package org.woowacourse.lunchbot.slack.dto.response.result;

import org.woowacourse.lunchbot.domain.Restaurant;
import org.woowacourse.lunchbot.slack.RestaurantType;
import org.woowacourse.lunchbot.slack.dto.response.block.BlockResponseFactory;
import org.woowacourse.lunchbot.slack.dto.response.common.ModalResponse;
import org.woowacourse.lunchbot.slack.dto.response.common.ModalSubmissionType;
import org.woowacourse.lunchbot.slack.fragment.block.Block;
import org.woowacourse.lunchbot.slack.fragment.block.SectionBlock;
import org.woowacourse.lunchbot.slack.fragment.composition.text.PlainText;
import org.woowacourse.lunchbot.slack.fragment.element.Element;
import org.woowacourse.lunchbot.slack.fragment.view.ModalView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResultResponseFactory {

    public static ModalResponse ofRandom(String triggerId, List<Restaurant> restaurants) {
        ModalView modalView = new ModalView(
                ModalSubmissionType.RECOMMEND_RESULT,
                new PlainText("오늘의 추천"),
                new PlainText("확인"),
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
                new PlainText("확인"),
                restaurants.stream()
                        .map(BlockResponseFactory::createRestaurantBlockWithoutImage)
                        .collect(Collectors.toList())
        );

        return new ModalResponse(triggerId, modalView);
    }

    public static ModalResponse of(String triggerId, String result) {
        ModalView modalView = new ModalView(
                ModalSubmissionType.EAT_TOGETHER,
                new PlainText("같이 먹어요 신청"),
                new PlainText("확인"),
                Arrays.asList(new SectionBlock(new PlainText(result)))
        );

        return new ModalResponse(triggerId, modalView);
    }

    public static ModalResponse of(String triggerId, List<List<String>> results) {
        List<Block> blocks = new ArrayList<>();
        StringBuilder names = new StringBuilder();
        for (List<String> result : results) {
            names.append(results.toString());
            names.append("\n");
        }

        ModalView modalView = new ModalView(
                ModalSubmissionType.EAT_TOGETHER,
                new PlainText("같이 먹어요 결과"),
                new PlainText("확인"),
                Arrays.asList(new SectionBlock(new PlainText(names.toString())))
        );
        return new ModalResponse(triggerId, modalView);
    }
}
