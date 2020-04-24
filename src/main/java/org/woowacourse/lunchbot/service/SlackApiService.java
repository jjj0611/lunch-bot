package org.woowacourse.lunchbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
import org.woowacourse.lunchbot.domain.UserProfile;

import java.util.Optional;

@Slf4j
@Service
public class SlackApiService {

    private static final Logger logger = LoggerFactory.getLogger(SlackApiService.class);

    private static final String BASE_URL = "https://slack.com/api";
    private static final String TOKEN = "Bearer " + System.getenv("BOT-TOKEN");

    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    public SlackApiService(ObjectMapper objectMapper, WebClient.Builder webclientBuilder) {
        this.objectMapper = objectMapper;
        this.webClient = initWebClient(webclientBuilder);
    }

    public Optional<UserProfile> getUserProfile(String userId) {
        Optional<UserProfile> optionalUserProfile;
        try {
            String response = sendRequestForUserProfile(userId);
            JsonNode jsonNode = objectMapper.readTree(response);
            System.out.println("profile json : " + jsonNode);
            optionalUserProfile = Optional.of(jsonToDto(jsonNode, UserProfile.class));
        } catch (JsonProcessingException je) {
            optionalUserProfile = Optional.empty();
        }
        return optionalUserProfile;
    }

    private String sendRequestForUserProfile(String userId) {
        return webClient.get()
                .uri("/users.profile.get",
                        uriBuilder -> uriBuilder.queryParam("user", userId)
                                .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public void openModal(Object dto) {
        send("/views.open", dto);
    }

    public void sendMessage(Object dto) {
        send("/chat.postMessage", dto);
    }

    public void send(String url, Object dto) {
        String response = webClient.post()
                .uri(url)
                .body(BodyInserters.fromValue(dto))
                .exchange().block().bodyToMono(String.class)
                .block();
        logger.info("response = {}", response);
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

    private <T> T jsonToDto(JsonNode json, Class<T> type) throws JsonProcessingException {
        return objectMapper.treeToValue(json, type);
    }

}
