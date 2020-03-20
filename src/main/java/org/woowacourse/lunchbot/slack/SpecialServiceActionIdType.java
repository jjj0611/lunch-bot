package org.woowacourse.lunchbot.slack;

public enum SpecialServiceActionIdType {

    RECOMMEND,
    APPLY,
    RESULT;

    public static SpecialServiceActionIdType of(String name) {
        return valueOf(name.toUpperCase());
    }

}
