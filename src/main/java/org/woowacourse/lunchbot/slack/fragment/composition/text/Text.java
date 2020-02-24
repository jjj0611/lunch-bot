package org.woowacourse.lunchbot.slack.fragment.composition.text;

public class Text {
    protected TextType type;
    protected String text;

    public Text() {
    }

    public Text(TextType type, String text) {
        this.type = type;
        this.text = text;
    }

    public TextType getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}

