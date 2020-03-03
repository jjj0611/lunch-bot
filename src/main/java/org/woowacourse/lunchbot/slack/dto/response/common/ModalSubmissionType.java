package org.woowacourse.lunchbot.slack.dto.response.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ModalSubmissionType {

    RECOMMEND_RESULT("recommend_result"),
    KOREAN_RESULT("korean_result"),
    CHINESE_RESULT("chinese_result"),
    WESTERN_RESULT("western_result"),
    INDIA_RESULT("india_result"),
    VIETNAM_RESULT("vietnam_result"),
    JAPANESE_RESULT("japanese_result");

    @JsonValue
    private String type;

    ModalSubmissionType(String type) {
        this.type = type;
    }

    public static ModalSubmissionType of(String name) {
        return valueOf(name.toUpperCase());
    }

    public String getType() {
        return type;
    }

}
