package org.woowacourse.lunchbot.slack.dto.response.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ModalSubmissionType {

    RECOMMEND_RESULT("recommend_result"),
    KOREAN_RESULT("korean_result"),
    CHINESE_RESULT("chinese_result"),
    SNACK_RESULT("snack_result"),
    WESTERN_RESULT("western_result"),
    JAPANESE_RESULT("japanese_result"),
    ETC_RESULT("etc_result"),
    EAT_TOGETHER("eat_together");

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
