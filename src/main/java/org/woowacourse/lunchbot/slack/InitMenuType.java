package org.woowacourse.lunchbot.slack;

import org.woowacourse.lunchbot.slack.dto.response.result.ChineseResultResponseFactory;
import org.woowacourse.lunchbot.slack.dto.response.result.IndiaResultResponseFactory;
import org.woowacourse.lunchbot.slack.dto.response.result.JapaneseResultResponseFactory;
import org.woowacourse.lunchbot.slack.dto.response.result.KoreanResultResponseFactory;
import org.woowacourse.lunchbot.slack.dto.response.result.RecommandResultResponseFactory;
import org.woowacourse.lunchbot.slack.dto.response.result.VietnamResultResponseFactory;
import org.woowacourse.lunchbot.slack.dto.response.result.WesternResultResponseFactory;

import java.util.function.Function;

public enum InitMenuType {
    RECOMMAND(RecommandResultResponseFactory::of),
    KOREAN(KoreanResultResponseFactory::of),
    CHINESE(ChineseResultResponseFactory::of),
    WESTERN(WesternResultResponseFactory::of),
    INDIA(IndiaResultResponseFactory::of),
    VIETNAM(VietnamResultResponseFactory::of),
    JAPANESE(JapaneseResultResponseFactory::of);

    private Function<String, Object> function;

    public static InitMenuType of(String name) {
        return valueOf(name.toUpperCase());
    }

    InitMenuType(Function<String, Object> function) {
        this.function = function;
    }

    public Object apply(String id) {
        return function.apply(id);
    }
}

