package org.woowacourse.lunchbot.slack;

public enum BlockIdType {
    RECOMMEND_AND_EAT_TOGETHER,
    RETRIEVE_MENU;

    public static BlockIdType of(String name) {
        return valueOf(name.toUpperCase());
    }
}
