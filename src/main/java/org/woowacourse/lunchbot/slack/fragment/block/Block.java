package org.woowacourse.lunchbot.slack.fragment.block;

public abstract class Block {
    private BlockType type;

    public Block() {

    }

    public Block(BlockType type) {
        this.type = type;
    }

    public BlockType getType() {
        return type;
    }
}
