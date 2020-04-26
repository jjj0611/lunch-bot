package org.woowacourse.lunchbot.service;

import org.springframework.stereotype.Service;
import org.woowacourse.lunchbot.domain.UserMatcher;
import org.woowacourse.lunchbot.domain.UserProfile;
import org.woowacourse.lunchbot.exception.NotAvailableTimeException;
import org.woowacourse.lunchbot.slack.dto.request.BlockActionRequest;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class EatTogetherService {

    private static final Function<LocalTime, Boolean> applyTime;
    private static final Function<LocalTime, Boolean> resultTime;
    private static final String SUCCESS_MESSAGE = "성공적으로 등록되었습니다.";
    private static final String ALREADY_APPLIED = "이미 신청한 사용자입니다";
    private static final String REQUEST_FAILURE_MESSAGE = "요청에 실패했습니다.";

    private final SlackApiService slackApiService;
    private final UserMatcher userMatcher;


    public EatTogetherService(SlackApiService slackApiService, UserMatcher userMatcher) {
        this.slackApiService = slackApiService;
        this.userMatcher = userMatcher;
    }


    static {
        applyTime = localTime -> (localTime.compareTo(LocalTime.of(9, 0)) >= 0
                            && localTime.compareTo(LocalTime.of(11, 20)) < 0)
                            || (localTime.compareTo(LocalTime.of(16, 0)) >= 0
                            && localTime.compareTo(LocalTime.of(17, 30)) < 0);

        resultTime = localTime -> (localTime.compareTo(LocalTime.of(11, 30)) >= 0
                    && localTime.compareTo(LocalTime.of(13, 30)) < 0)
                    || (localTime.compareTo(LocalTime.of(18, 0)) >= 0
                    && localTime.compareTo(LocalTime.of(20, 0)) < 0);
    }

    public String apply(BlockActionRequest request) {
        if (!applyTime.apply(LocalTime.now(ZoneId.of("Asia/Seoul")))) {
            throw new NotAvailableTimeException(request.getTriggerId());
        }
        Optional<UserProfile> userProfile = slackApiService.getUserProfile(request.getUserId());
        if (userProfile.isPresent()) {
            return userMatcher.apply(userProfile.get()) ? SUCCESS_MESSAGE : ALREADY_APPLIED;
        }
        return REQUEST_FAILURE_MESSAGE;
    }

    public List<List<UserProfile>> getMatchedUsers(BlockActionRequest request) {
        if (!resultTime.apply(LocalTime.now(ZoneId.of("Asia/Seoul")))) {
            throw new NotAvailableTimeException(request.getTriggerId());
        }
        List<List<UserProfile>> matchedUsers = userMatcher.getMatchedUsers();
        if (matchedUsers.isEmpty()) {
            throw new NotAvailableTimeException(request.getTriggerId());
        }
        return userMatcher.getMatchedUsers();
    }

    public void updateResults() {
        userMatcher.resetMatchedUsers();
        userMatcher.generateMatchedUsers();
        userMatcher.resetAppliedUsers();
    }
}
