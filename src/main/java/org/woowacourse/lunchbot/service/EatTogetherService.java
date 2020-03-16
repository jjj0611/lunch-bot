package org.woowacourse.lunchbot.service;

import org.springframework.stereotype.Service;
import org.woowacourse.lunchbot.domain.UserMatcher;
import org.woowacourse.lunchbot.domain.UserProfile;
import org.woowacourse.lunchbot.slack.dto.request.BlockActionRequest;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalQuery;
import java.util.List;
import java.util.Optional;

@Service
public class EatTogetherService {

    private static final TemporalQuery<Boolean> applyTime;
    private static final TemporalQuery<Boolean> resultTime;
    private static final String SUCCESS_MESSAGE = "성공적으로 등록되었습니다.";
    private static final String ALREADY_APPLIED = "이미 신청한 사용자입니다";
    private static final String REQUEST_FAILURE_MESSAGE = "요청에 실패했습니다.";
    private static final String NOT_AVAILABLE_TIME = "신청 가능한 시간이 아닙니다.";
    public static final String NO_ONE_APPLIED = "신청자가 없습니다.";

    private final SlackApiService slackApiService;
    private final UserMatcher userMatcher;


    public EatTogetherService(SlackApiService slackApiService, UserMatcher userMatcher) {
        this.slackApiService = slackApiService;
        this.userMatcher = userMatcher;
    }


    static {
        applyTime =
                temporal -> {
                    LocalTime now = LocalTime.now();
                    return (now.compareTo(LocalTime.of(9, 0)) >= 0
                            && now.compareTo(LocalTime.of(11, 20)) < 0)
                            || (now.compareTo(LocalTime.of(16, 0)) >= 0
                            && now.compareTo(LocalTime.of(17, 30)) < 0);
                };

        resultTime = temporal -> {
            LocalTime now = LocalTime.now();
            return (now.compareTo(LocalTime.of(11, 30)) >= 0
                    && now.compareTo(LocalTime.of(13, 30)) < 0)
                    || (now.compareTo(LocalTime.of(18, 0)) >= 0
                    && now.compareTo(LocalTime.of(20, 0)) < 0);
        };
    }

    public String apply(BlockActionRequest request) {
        if (!applyTime.queryFrom(ZonedDateTime.now())) {
            return NOT_AVAILABLE_TIME;
        }
        Optional<UserProfile> userProfile = slackApiService.getUserProfile(request.getUserId());
        if (userProfile.isPresent()) {
            return userMatcher.apply(userProfile.get()) ? SUCCESS_MESSAGE : ALREADY_APPLIED;
        }
        return REQUEST_FAILURE_MESSAGE;
    }

    public List<List<UserProfile>> getMatchedUsers() {
        if (!resultTime.queryFrom(ZonedDateTime.now())) {
            throw new IllegalArgumentException(NOT_AVAILABLE_TIME);
        }
        List<List<UserProfile>> matchedUsers = userMatcher.getMatchedUsers();
        if (matchedUsers.isEmpty()) {
            throw new IllegalArgumentException(NO_ONE_APPLIED);
        }
        return userMatcher.getMatchedUsers();
    }
}
