package org.woowacourse.lunchbot.exception;

public class FailToFetchRestaurantsException extends RuntimeException {

    private static final String MESSAGE = "데이터를 google로부터 읽어오는데 실패했습니다.";

    public FailToFetchRestaurantsException(String exceptionMessage) {
        super(MESSAGE + "\n" + exceptionMessage);
    }

}
