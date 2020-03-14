package org.woowacourse.lunchbot.service;

import org.springframework.stereotype.Service;
import org.woowacourse.lunchbot.domain.Restaurant;
import org.woowacourse.lunchbot.domain.UserProfile;
import org.woowacourse.lunchbot.slack.BlockIdType;
import org.woowacourse.lunchbot.slack.EventType;
import org.woowacourse.lunchbot.slack.RestaurantType;
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
    private final RestaurantService restaurantService;
    private final EatTogetherService eatTogetherService;

    public SlackBotService(SlackApiService slackApiService, RestaurantService restaurantService, EatTogetherService eatTogetherService) {
        this.slackApiService = slackApiService;
        this.restaurantService = restaurantService;
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
//            case RECOMMEND_MENU:
//                List<Restaurant> recommendRestaurants = restaurantService.findRecommends();
//                modalResponse = ResultResponseFactory.ofRecommend(
//                        request.getTriggerId(), recommendRestaurants);
//                send("/views.open", modalResponse);
//                break;
            case RETRIEVE_MENU:
                RestaurantType restaurantType = RestaurantType.from(request.getActionId());
                List<Restaurant> restaurants = restaurantService.findBy(restaurantType);
                modalResponse = ResultResponseFactory.ofRestaurants(triggerId, restaurantType, restaurants);
                slackApiService.send("/views.open", modalResponse);
                break;
            case RECOMMEND_AND_EAT_TOGETHER:
                if (request.getActionId().equals("recommend")) {
                    modalResponse = ResultResponseFactory.ofRecommend(triggerId, restaurantService.findRecommends());
                }

                if (request.getActionId().equals("apply")) {
                    String appliedMessage = eatTogetherService.apply(request);
                    modalResponse = ResultResponseFactory.ofEatTogetherApplied(triggerId, appliedMessage);
                }

                if (request.getActionId().equals("result")) {
                    try {
                        List<List<UserProfile>> matchedUsers = eatTogetherService.getMatchedUsers();
                        modalResponse = ResultResponseFactory.ofEatTogetherResult(triggerId, matchedUsers);
                    } catch (IllegalArgumentException ie) {
                        modalResponse = ResultResponseFactory.ofError(triggerId, ie.getMessage());
                    }
                }
                slackApiService.send("/views.open", modalResponse);
        }
    }
}
