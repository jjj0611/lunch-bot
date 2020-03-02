package org.woowacourse.lunchbot.slack;

public enum RequestType {
    URL_VERIFICATION,
    EVENT_CALLBACK,
    BLOCK_ACTIONS,
    VIEW_SUBMISSION;


    public static RequestType of(String name) {
        return valueOf(name.toUpperCase());
    }
}
