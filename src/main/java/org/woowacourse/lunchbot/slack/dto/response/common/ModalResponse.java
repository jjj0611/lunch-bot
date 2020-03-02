package org.woowacourse.lunchbot.slack.dto.response.common;

import org.woowacourse.lunchbot.slack.fragment.view.ModalView;

public class ModalResponse {
    private String triggerId;
    private ModalView view;

    public ModalResponse() {
    }

    public ModalResponse(String triggerId, ModalView view) {
        this.triggerId = triggerId;
        this.view = view;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public ModalView getView() {
        return view;
    }
}

