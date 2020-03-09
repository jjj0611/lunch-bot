package org.woowacourse.lunchbot.domain;

import com.google.api.client.util.ArrayMap;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.List;

public class EatTogether {
    static Map<String, String> matchedUser;
    static List<String> applyUser;
    static List<List<LocalTime>> timeScope;

    static {
        matchedUser = new ArrayMap<>();
        applyUser = new ArrayList<>();
        timeScope = new ArrayList<>(new ArrayList<>());
        timeScope.add(Arrays.asList(LocalTime.of(9, 0), LocalTime.of(11, 20)));
        timeScope.add(Arrays.asList(LocalTime.of(16, 0), LocalTime.of(17, 30)));
    }

    public static String getResult(String userId) {
        if (!validateTimeScope()) {
            return "신청 가능한 시간이 아닙니다.";
        }

        if (addApplyUser(userId)) {
            return "성공적으로 등록되었습니다.";
        }
        return "이미 등록된 사용자입니다.";
    }

    public static boolean addApplyUser(String userId) {
        if (applyUser.contains(userId)) {
            return false;
        }
        applyUser.add(userId);
        return true;
    }

    public static boolean validateTimeScope() {
        LocalTime time = LocalTime.now();
        LocalTime startTime = timeScope.get(0).get(0);
        LocalTime endTime = timeScope.get(0).get(1);
        if (isDinner(time)) {
            startTime = timeScope.get(1).get(0);
            endTime = timeScope.get(1).get(1);
        }

        if (time.isAfter(startTime) && time.isBefore(endTime)) {
            return true;
        }
        return false;
    }

    public static boolean isDinner(LocalTime time) {
        if (time.isAfter(timeScope.get(1).get(0))) {
            return true;
        }
        return false;
    }
}
