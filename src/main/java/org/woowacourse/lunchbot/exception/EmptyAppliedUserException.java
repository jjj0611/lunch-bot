package org.woowacourse.lunchbot.exception;

public class EmptyAppliedUserException extends RuntimeException {

    private static final String MESSAGE = "신청자가 없습니다.";

    private String triggerId;

    public EmptyAppliedUserException(String triggerId) {
        super(MESSAGE);
        this.triggerId = triggerId;
    }

    public String getTriggerId() {
        return triggerId;
    }
}
