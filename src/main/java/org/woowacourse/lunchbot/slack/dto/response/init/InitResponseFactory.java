package org.woowacourse.lunchbot.slack.dto.response.init;

import org.woowacourse.lunchbot.slack.dto.response.block.BlockResponseFactory;
import org.woowacourse.lunchbot.slack.fragment.block.Block;

import java.util.Arrays;
import java.util.List;

public class InitResponseFactory {
    public static InitResponse of(String channel) {

        List<Block> blocks = Arrays.asList(
                BlockResponseFactory.createInitActionBlock("retrieve_menu")
        );

        return new InitResponse(channel, blocks);
    }
}
