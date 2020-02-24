package org.woowacourse.lunchbot.slack.fragment.element;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ElementType {
    IMAGE("image"),
    BUTTON("button");

    @JsonValue
    private String type;

    ElementType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
