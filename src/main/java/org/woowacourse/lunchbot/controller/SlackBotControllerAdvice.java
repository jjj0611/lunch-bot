package org.woowacourse.lunchbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.woowacourse.lunchbot.exception.EmptyAppliedUserException;
import org.woowacourse.lunchbot.exception.FailToFetchRestaurantsException;
import org.woowacourse.lunchbot.exception.NotAvailableTimeException;
import org.woowacourse.lunchbot.service.SlackApiService;
import org.woowacourse.lunchbot.slack.dto.response.Message;
import org.woowacourse.lunchbot.slack.dto.response.common.ModalResponse;
import org.woowacourse.lunchbot.slack.dto.response.result.ResultResponseFactory;

@RestControllerAdvice
@RequiredArgsConstructor
public class SlackBotControllerAdvice {

    private final SlackApiService slackApiService;

    @ExceptionHandler(NotAvailableTimeException.class)
    public ResponseEntity notAvailable(NotAvailableTimeException ne) {
        String triggerId = ne.getTriggerId();
        String result = ne.getMessage();
        ModalResponse modalResponse = ResultResponseFactory.ofError(triggerId, result);
        slackApiService.openModal(modalResponse);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(EmptyAppliedUserException.class)
    public ResponseEntity emptyResult(EmptyAppliedUserException ee) {
        String triggerId = ee.getTriggerId();
        String result = ee.getMessage();
        ModalResponse modalResponse = ResultResponseFactory.ofError(triggerId, result);
        slackApiService.openModal(modalResponse);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(FailToFetchRestaurantsException.class)
    public ResponseEntity failToFetchRestaurants(FailToFetchRestaurantsException fe) {
        String errorMessage = fe.getMessage();
        Message message = new Message("lunch-bot", errorMessage);
        slackApiService.sendMessage(message);
        return ResponseEntity.ok().build();
    }
}
