package org.woowacourse.lunchbot.slack.fragment.composition.text;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TextType {
    PLAIN_TEXT("plain_text"),
    MRKDWN("mrkdwn");

    @JsonValue
    private String type;

    TextType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
