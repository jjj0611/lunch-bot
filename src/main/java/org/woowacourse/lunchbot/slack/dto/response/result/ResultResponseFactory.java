package org.woowacourse.lunchbot.slack.dto.response.result;

import org.woowacourse.lunchbot.domain.Restaurant;
import org.woowacourse.lunchbot.domain.UserProfile;
import org.woowacourse.lunchbot.slack.RestaurantType;
import org.woowacourse.lunchbot.slack.dto.response.block.BlockResponseFactory;
import org.woowacourse.lunchbot.slack.dto.response.common.ModalResponse;
import org.woowacourse.lunchbot.slack.dto.response.common.ModalSubmissionType;
import org.woowacourse.lunchbot.slack.fragment.block.SectionBlock;
import org.woowacourse.lunchbot.slack.fragment.composition.text.PlainText;
import org.woowacourse.lunchbot.slack.fragment.composition.text.Text;
import org.woowacourse.lunchbot.slack.fragment.view.ModalView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResultResponseFactory {

    public static ModalResponse ofRecommend(String triggerId, List<Restaurant> restaurants) {
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

    public static ModalResponse ofRestaurants(String triggerId, RestaurantType restaurantType, List<Restaurant> restaurants) {
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

    public static ModalResponse ofEatTogetherApplied(String triggerId, String result) {
        ModalView modalView = new ModalView(
                ModalSubmissionType.EAT_TOGETHER,
                new PlainText("같이 먹어요 신청"),
                new PlainText("확인"),
                Arrays.asList(new SectionBlock(new PlainText(result)))
        );

        return new ModalResponse(triggerId, modalView);
    }

    public static ModalResponse ofEatTogetherResult(String triggerId, List<List<UserProfile>> matchedUsers) {
        List<Text> results = new ArrayList<>();
        for (List<UserProfile> matchedUserTeam : matchedUsers) {
            results.add(PlainText.from(matchedUserTeam));
        }
        ModalView modalView = new ModalView(
                ModalSubmissionType.EAT_TOGETHER,
                new PlainText("같이 먹어요 결과"),
                new PlainText("확인"),
                Arrays.asList(new SectionBlock(new PlainText("결과입니다"), results))
        );
        return new ModalResponse(triggerId, modalView);
    }

    public static ModalResponse ofError(String triggerId, String message) {
        ModalView modalView = new ModalView(
                ModalSubmissionType.EAT_TOGETHER,
                new PlainText("잘못된 요청입니다."),
                new PlainText("확인"),
                Arrays.asList(new SectionBlock(new PlainText(message)))
        );

        return new ModalResponse(triggerId, modalView);
    }
}
