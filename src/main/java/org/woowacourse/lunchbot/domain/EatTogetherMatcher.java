package org.woowacourse.lunchbot.domain;

import java.util.ArrayList;
import java.util.List;

public class EatTogetherMatcher {
    private static List<List<String>> matchedUser;
    private static final int offset;

    static {
        matchedUser = new ArrayList<>(new ArrayList<>());
        offset = 4;
    }

    public void match() {
        EatTogether.shuffle();

        int length = EatTogether.getUserLength();
        int index = 0;
        while (length > (index + offset)) {
            matchedUser.add(EatTogether.sublist(index, index + offset));
            index += offset;
        }

        if (length > index) {
            if (length - index <= 2) { // 1, 2명 남았을 때는 이전 그룹과 합치기
                matchedUser.get(matchedUser.size() - 1).addAll(EatTogether.sublist(index, length));
            }
            if (length - index > 2) { // 3명일 때는 새로운 그룹으로
                matchedUser.add(EatTogether.sublist(index, length));
            }
        }
    }

    public List<List<String>> getResult() {
        return matchedUser;
    }
}
