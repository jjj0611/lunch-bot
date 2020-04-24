package org.woowacourse.lunchbot;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.woowacourse.lunchbot.service.EatTogetherService;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeTest {
    private boolean applyTime(LocalTime now) {
        return (now.compareTo(LocalTime.of(9, 0)) >= 0
                && now.compareTo(LocalTime.of(11, 20)) < 0)
                || (now.compareTo(LocalTime.of(16, 0)) >= 0
                && now.compareTo(LocalTime.of(17, 30)) < 0);
    }

    private boolean resultTime(LocalTime now) {
            return (now.compareTo(LocalTime.of(11, 30)) >= 0
                    && now.compareTo(LocalTime.of(13, 30)) < 0)
                    || (now.compareTo(LocalTime.of(18, 0)) >= 0
                    && now.compareTo(LocalTime.of(20, 0)) < 0);

    }

    @ParameterizedTest
    @MethodSource("applyTimes")
    void applyTest(LocalTime localTime, boolean expected) {
        assertThat(applyTime(localTime)).isEqualTo(expected);
    }

    static Stream<Arguments> applyTimes() {
//        LocalTime.of(int hour, int minute, int second, int nanoOfSecond)
        return Stream.of(
                Arguments.of(LocalTime.of(9, 0, 0, 0), true),
                Arguments.of(LocalTime.now(ZoneId.of("Asia/Seoul")), true)
        );
    }
}
