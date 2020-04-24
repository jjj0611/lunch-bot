package org.woowacourse.lunchbot.service;

import org.springframework.stereotype.Service;
import org.woowacourse.lunchbot.domain.UserProfile;
import org.woowacourse.lunchbot.slack.BlockIdType;
import org.woowacourse.lunchbot.slack.EventType;
import org.woowacourse.lunchbot.slack.SpecialServiceActionIdType;
import org.woowacourse.lunchbot.slack.dto.request.BlockActionRequest;
import org.woowacourse.lunchbot.slack.dto.request.EventCallBackRequest;
import org.woowacourse.lunchbot.slack.dto.response.common.ModalResponse;
import org.woowacourse.lunchbot.slack.dto.response.init.InitHomeMenuResponseFactory;
import org.woowacourse.lunchbot.slack.dto.response.init.InitResponseFactory;
import org.woowacourse.lunchbot.slack.dto.response.result.ResultResponseFactory;

import java.util.List;

@Service
public class SlackBotService {

    private final SlackApiService slackApiService;
    private final EatTogetherService eatTogetherService;

    public SlackBotService(SlackApiService slackApiService, EatTogetherService eatTogetherService) {
        this.slackApiService = slackApiService;
        this.eatTogetherService = eatTogetherService;
    }

    public void showMenu(EventCallBackRequest request) {
        switch (EventType.of(request.getType())) {
            case APP_MENTION:
                slackApiService.send("/chat.postMessage", InitResponseFactory.of(request.getChannel()));
                break;
            case APP_HOME_OPENED:
                slackApiService.send("/views.publish", InitHomeMenuResponseFactory.of(request.getUserId()));
        }
    }

    public void showModal(BlockActionRequest request) {
        String triggerId = request.getTriggerId();
        ModalResponse modalResponse = null;
        switch (BlockIdType.of(request.getBlockId())) {
            case SPECIAL_SERVICE:
                switch (SpecialServiceActionIdType.of(request.getActionId())) {
                    case APPLY:
                        String appliedMessage = eatTogetherService.apply(request);
                        modalResponse = ResultResponseFactory.ofEatTogetherApplied(triggerId, appliedMessage);
                        break;
                    case RESULT:
                        List<List<UserProfile>> matchedUsers = eatTogetherService.getMatchedUsers(request);
                        modalResponse = ResultResponseFactory.ofEatTogetherResult(triggerId, matchedUsers);
                }
        }
        slackApiService.openModal(modalResponse);
    }
}
