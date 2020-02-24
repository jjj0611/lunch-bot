package org.woowacourse.lunchbot.slack;

public enum EventType {
    APP_MENTION,
    APP_HOME_OPENED;

    public static EventType of(String name) {
        return valueOf(name.toUpperCase());
    }
}
