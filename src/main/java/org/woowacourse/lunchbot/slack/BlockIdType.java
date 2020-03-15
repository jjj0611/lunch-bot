package org.woowacourse.lunchbot.slack;

public enum BlockIdType {
    SPECIAL_SERVICE,
    RETRIEVE_MENU;

    public static BlockIdType of(String name) {
        return valueOf(name.toUpperCase());
    }
}
