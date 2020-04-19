package org.woowacourse.lunchbot.exception;

public class NotAvailableTimeException extends RuntimeException {

    private static final String MESSAGE = "신청 가능한 시간이 아닙니다.";

    private String triggerId;

    public NotAvailableTimeException(String triggerId) {
        super(MESSAGE);
        this.triggerId = triggerId;
    }

    public String getTriggerId() {
        return triggerId;
    }
}
