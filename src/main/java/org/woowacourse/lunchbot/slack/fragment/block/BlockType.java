package org.woowacourse.lunchbot.slack.fragment.block;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BlockType {

    SECTION("section"),
    DIVIDER("divider"),
    ACTIONS("actions");

    @JsonValue
    private String type;

    BlockType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
