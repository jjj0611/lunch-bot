package org.woowacourse.lunchbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.woowacourse.lunchbot.domain.Restaurant;
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

@Slf4j
@Service
public class SlackBotService {

    private static final Logger logger = LoggerFactory.getLogger(SlackBotService.class);

    private static final String BASE_URL = "https://slack.com/api";
    private static final String TOKEN = "Bearer " + "xoxb-946531805872-946555368885-pPUBbFvqwgg35vvv5kRBWBLm";

    private final ObjectMapper objectMapper;
    private final WebClient webClient;
    private final RestaurantService restaurantService;

    public SlackBotService(ObjectMapper objectMapper, WebClient.Builder webclientBuilder, RestaurantService restaurantService) {
        this.objectMapper = objectMapper;
        this.webClient = initWebClient(webclientBuilder);
        this.restaurantService = restaurantService;
    }

    private WebClient initWebClient(WebClient.Builder webClientBuilder) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(config ->
                        config.customCodecs().register(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON))
                ).build();
        return webClientBuilder
                .exchangeStrategies(strategies)
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.AUTHORIZATION, TOKEN)
                .build();
    }

    public void showMenu(EventCallBackRequest request) {
        switch (EventType.of(request.getType())) {
            case APP_MENTION:
                send("/chat.postMessage", InitResponseFactory.of(request.getChannel()));
                break;
            case APP_HOME_OPENED:
                send("/views.publish", InitHomeMenuResponseFactory.of(request.getUserId()));
        }
    }

    public void showModal(BlockActionRequest request) {
        switch (BlockIdType.of(request.getBlockId())) {
            case RECOMMEND_MENU:
                List<Restaurant> recommendRestaurants = restaurantService.findRecommends();
                ModalResponse recommendModalResponse = ResultResponseFactory.ofRandom(
                        request.getTriggerId(), recommendRestaurants);
                send("/views.open", recommendModalResponse);
                break;
            case RETRIEVE_MENU:
                RestaurantType restaurantType = RestaurantType.from(request.getActionId());
                List<Restaurant> restaurants = restaurantService.findBy(restaurantType);
                ModalResponse modalResponse = ResultResponseFactory.of(
                        request.getTriggerId(), restaurantType, restaurants);
                send("/views.open", modalResponse);
                break;
            case EAT_TOGETHER:
                // 신청 추가
        }
    }

    private void send(String url, Object dto) {
        String response = webClient.post()
                .uri(url)
                .body(BodyInserters.fromValue(dto))
                .exchange().block().bodyToMono(String.class)
                .block();
        logger.info("response = {}", response);
    }

}
