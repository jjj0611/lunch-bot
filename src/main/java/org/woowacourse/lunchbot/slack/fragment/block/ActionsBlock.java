package org.woowacourse.lunchbot.slack.fragment.block;

import org.woowacourse.lunchbot.slack.fragment.element.Element;

import java.util.List;

public class ActionsBlock extends Block {
    private String blockId;
    private List<Element> elements;

    public ActionsBlock() {
        super(BlockType.ACTIONS);
    }

    public ActionsBlock(String blockId, List<Element> elements) {
        super(BlockType.ACTIONS);
        this.blockId = blockId;
        this.elements = elements;
    }

    public List<Element> getElements() {
        return elements;
    }

    public String getBlockId() {
        return blockId;
    }
}

