package org.woowacourse.lunchbot.slack;

public enum RestaurantType {

    RECOMMEND("recommend", "recommend_result", "오늘의 추천"),
    KOREAN("korean", "korean_result", "한식"),
    CHINESE("chinese", "china_result", "중식"),
    WESTERN("western", "western_result", "양식"),
    INDIA("india", "india_result", "인도식"),
    VIETNAM("vietnam", "vietnam_result", "베트남식"),
    JAPANESE("japan", "japan_result", "일식");

    private String type;
    private String modalSubmissionType;
    private String title;

    RestaurantType(String type, String modalSubmissionType, String title) {
        this.type = type;
        this.modalSubmissionType = modalSubmissionType;
        this.title = title;
    }

    public static RestaurantType from(String name) {
        return valueOf(name.toUpperCase());
    }

    public String getModalSubmissionType() {
        return modalSubmissionType;
    }

    public String getTitle() {
        return title;
    }
}
