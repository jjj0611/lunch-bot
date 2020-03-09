package org.woowacourse.lunchbot.slack.dto.response.init;

import org.woowacourse.lunchbot.slack.fragment.block.Block;

import java.util.List;

public class InitResponse {
    private String channel;
    private List<Block> blocks;

    public InitResponse() {
    }

    public InitResponse(String channel, List<Block> blocks) {
        this.channel = channel;
        this.blocks = blocks;
    }

    public String getChannel() {
        return channel;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
