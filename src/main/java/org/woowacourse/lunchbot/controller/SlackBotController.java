package org.woowacourse.lunchbot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlackBotController {

    @PostMapping("/slack/events")
    public ResponseEntity<?> handleEvents(@RequestBody JsonNode reqJson) {
        System.out.println(reqJson);
        return ResponseEntity.ok(reqJson.get("challenge"));
    }

}

