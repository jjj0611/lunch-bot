package org.woowacourse.lunchbot.slack.dto.response;

import org.woowacourse.lunchbot.slack.fragment.view.HomeView;

public class HomeMenuResponse {
    private String userId;
    private HomeView view;

    public HomeMenuResponse() {
    }

    public HomeMenuResponse(String userId, HomeView view) {
        this.userId = userId;
        this.view = view;
    }

    public String getUserId() {
        return userId;
    }

    public HomeView getView() {
        return view;
    }
}
