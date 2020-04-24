package org.woowacourse.lunchbot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.woowacourse.lunchbot.service.EatTogetherService;
import org.woowacourse.lunchbot.service.SlackBotService;
import org.woowacourse.lunchbot.slack.RequestType;
import org.woowacourse.lunchbot.slack.dto.request.EventCallBackRequest;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Map;

@RestController
public class SlackBotController {

    private final ObjectMapper objectMapper;
    private final SlackBotService slackBotService;

    public SlackBotController(ObjectMapper objectMapper, SlackBotService slackBotService) {
        this.objectMapper = objectMapper;
        this.slackBotService = slackBotService;
    }

    @PostMapping("/slack/action")
    public ResponseEntity<?> handleEvents(@RequestBody JsonNode reqJson) throws JsonProcessingException {
        System.out.println("@@@@@ Controller slack/action ");
        switch (RequestType.of(reqJson.get("type").asText())) {
            case URL_VERIFICATION:
                return ResponseEntity.ok(reqJson.get("challenge"));
            case EVENT_CALLBACK:
                slackBotService.showMenu(jsonToDto(reqJson, EventCallBackRequest.class));
                return ResponseEntity.ok().build();
            default:
                return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/slack/interaction")
    public ResponseEntity interaction(@RequestParam Map<String, String> req) throws IOException {
        System.out.println("@@@@@ Controller slack/interaction ");
        System.out.println("LocalTime.now(): "+EatTogetherService.applyTime.apply(LocalTime.now()));
        System.out.println("LocalTime.now(Asia Seoul): "+EatTogetherService.applyTime.apply(LocalTime.now(ZoneId.of("Asia/Seoul"))));
        System.out.println("LocalTime 09:00 "+EatTogetherService.applyTime.apply(LocalTime.of(9, 0, 0, 0)));
        System.out.println("LocalTime 12:00 "+EatTogetherService.applyTime.apply(LocalTime.of(12, 0, 0, 0)));
        System.out.println("LocalTime 16:00 "+EatTogetherService.applyTime.apply(LocalTime.of(16, 0, 0, 0)));
        return ResponseEntity.ok().build();
    }

    private <T> T jsonToDto(JsonNode json, Class<T> type) throws JsonProcessingException {
        return objectMapper.treeToValue(json, type);
    }

}

