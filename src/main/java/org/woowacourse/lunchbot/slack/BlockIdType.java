package org.woowacourse.lunchbot.slack;

public enum BlockIdType {

    EAT_TOGETHER,
    RECOMMEND_MENU,
    RETRIEVE_MENU;


    public static BlockIdType of(String name) {
        return valueOf(name.toUpperCase());
    }

}
