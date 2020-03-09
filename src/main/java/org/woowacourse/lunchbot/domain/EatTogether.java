package org.woowacourse.lunchbot.domain;

import com.google.api.client.util.ArrayMap;

import java.time.LocalTime;
import java.util.*;

public class EatTogether {
    private static List<String> applyUser;
    private static List<List<LocalTime>> timeScope;
    private static final int LUNCH_INDEX;
    private static final int DINNER_INDEX;

    static {
        applyUser = new ArrayList<>();
        timeScope = new ArrayList<>(new ArrayList<>());
        timeScope.add(Arrays.asList(LocalTime.of(9, 0), LocalTime.of(11, 20)));
        timeScope.add(Arrays.asList(LocalTime.of(16, 0), LocalTime.of(17, 30)));
        LUNCH_INDEX = 0;
        DINNER_INDEX = 1;
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
        LocalTime startTime = timeScope.get(LUNCH_INDEX).get(0);
        LocalTime endTime = timeScope.get(LUNCH_INDEX).get(1);
        if (isDinner(time)) {
            startTime = timeScope.get(DINNER_INDEX).get(0);
            endTime = timeScope.get(DINNER_INDEX).get(1);
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

    public static void shuffle() {
        Collections.shuffle(applyUser);
    }

    public static int getUserLength() {
        return applyUser.size();
    }

    public static List<String> sublist(int startIndex, int endIndex) {
        if (endIndex < startIndex || startIndex < 0 || endIndex > applyUser.size()) {
            return new ArrayList<>();
        }
        return applyUser.subList(startIndex, endIndex);
    }
}
