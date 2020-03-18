package org.woowacourse.lunchbot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.woowacourse.lunchbot.service.SlackBotService;
import org.woowacourse.lunchbot.slack.RequestType;
import org.woowacourse.lunchbot.slack.dto.request.BlockActionRequest;
import org.woowacourse.lunchbot.slack.dto.request.EventCallBackRequest;

import java.io.IOException;
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
        JsonNode reqJson = objectMapper.readTree(req.get("payload"));
        switch (RequestType.of(reqJson.get("type").asText())) {
            case BLOCK_ACTIONS:
                slackBotService.showModal(jsonToDto(reqJson, BlockActionRequest.class));
                return ResponseEntity.ok().build();
            default:
                return ResponseEntity.badRequest().build();
        }
    }

    private <T> T jsonToDto(JsonNode json, Class<T> type) throws JsonProcessingException {
        return objectMapper.treeToValue(json, type);
    }

}

