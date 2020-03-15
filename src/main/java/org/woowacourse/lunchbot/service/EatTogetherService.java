package org.woowacourse.lunchbot.service;

import org.springframework.stereotype.Service;
import org.woowacourse.lunchbot.domain.UserMatcher;
import org.woowacourse.lunchbot.domain.UserProfile;
import org.woowacourse.lunchbot.slack.dto.request.BlockActionRequest;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EatTogetherService {

    private static final Map<String, List<LocalTime>> TIME_SCOPE = initializeTimeScope();
    private static final String LUNCH_APPLY = "lunchApply";
    private static final String DINNER_APPLY = "dinnerApply";
    private static final String LUNCH_RESULT = "lunchResult";
    private static final String DINNER_RESULT = "dinnerResult";
    private static final int START_TIME_INDEX = 0;
    private static final int END_TIME_INDEX = 1;
    private static final String SUCCESS_MESSAGE = "성공적으로 등록되었습니다.";
    private static final String ALREADY_APPLIED = "이미 신청한 사용자입니다";
    private static final String REQUEST_FAILURE_MESSAGE = "요청에 실패했습니다.";
    private static final String NOT_AVAILABLE_TIME = "신청 가능한 시간이 아닙니다.";
    public static final String NO_ONE_APPLIED = "신청자가 없습니다.";

    private final SlackApiService slackApiService;
    private final UserMatcher userMatcher;

    private static Map<String, List<LocalTime>> initializeTimeScope() {
        Map<String, List<LocalTime>> timeScope = new HashMap<>();
        List<LocalTime> lunchApply = Arrays.asList(LocalTime.of(9, 0), LocalTime.of(11, 20));
        List<LocalTime> dinnerApply = Arrays.asList(LocalTime.of(16, 0), LocalTime.of(17, 30));
        List<LocalTime> lunchResult = Arrays.asList(LocalTime.of(11, 30), LocalTime.of(13, 30));
        List<LocalTime> dinnerResult = Arrays.asList(LocalTime.of(18, 0), LocalTime.of(20, 0));
        timeScope.put(LUNCH_APPLY, lunchApply);
        timeScope.put(DINNER_APPLY, dinnerApply);
        timeScope.put(LUNCH_RESULT, lunchResult);
        timeScope.put(DINNER_RESULT, dinnerResult);
        return timeScope;
    }

    public EatTogetherService(SlackApiService slackApiService, UserMatcher userMatcher) {
        this.slackApiService = slackApiService;
        this.userMatcher = userMatcher;
    }

    public String apply(BlockActionRequest request) {
        if (!validateApplyTimeScope()) {
            return NOT_AVAILABLE_TIME;
        }
        Optional<UserProfile> userProfile = slackApiService.getUserProfile(request.getUserId());
        if (userProfile.isPresent()) {
            return userMatcher.apply(userProfile.get()) ? SUCCESS_MESSAGE : ALREADY_APPLIED;
        }
        return REQUEST_FAILURE_MESSAGE;
    }

    public List<List<UserProfile>> getMatchedUsers() {
        if (!validateResultTimeScope()) {
            throw new IllegalArgumentException(NOT_AVAILABLE_TIME);
        }
        List<List<UserProfile>> matchedUsers = userMatcher.getMatchedUsers();
        if (matchedUsers.isEmpty()) {
            throw new IllegalArgumentException(NO_ONE_APPLIED);
        }
        return userMatcher.getMatchedUsers();
    }

    private boolean validateApplyTimeScope() {
        return isBetween(TIME_SCOPE.get(LUNCH_APPLY)) || isBetween(TIME_SCOPE.get(DINNER_APPLY));
    }

    private boolean validateResultTimeScope() {
        return isBetween(TIME_SCOPE.get(LUNCH_RESULT)) || isBetween(TIME_SCOPE.get(DINNER_RESULT));
    }

    private boolean isBetween(List<LocalTime> times) {
        LocalTime now = LocalTime.now();
        return now.isAfter(times.get(START_TIME_INDEX)) && now.isBefore(times.get(END_TIME_INDEX));
    }
}
