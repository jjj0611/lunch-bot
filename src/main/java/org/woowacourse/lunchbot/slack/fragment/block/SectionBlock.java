package org.woowacourse.lunchbot.slack.fragment.block;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.woowacourse.lunchbot.slack.fragment.composition.text.Text;
import org.woowacourse.lunchbot.slack.fragment.element.Element;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionBlock extends Block {
    private Text text;
    private List<Text> fields;
    private Element accessory;

    public SectionBlock() {
        super(BlockType.SECTION);
    }

    public SectionBlock(Text text) {
        super(BlockType.SECTION);
        this.text = text;
    }

    public SectionBlock(Text text, List<Text> fields) {
        super(BlockType.SECTION);
        this.text = text;
        this.fields = fields;
    }

    public SectionBlock(Text text, Element accessory) {
        super(BlockType.SECTION);
        this.text = text;
        this.accessory = accessory;
    }

    public Text getText() {
        return text;
    }

    public List<Text> getFields() {
        return fields;
    }

    public Element getAccessory() {
        return accessory;
    }
}

