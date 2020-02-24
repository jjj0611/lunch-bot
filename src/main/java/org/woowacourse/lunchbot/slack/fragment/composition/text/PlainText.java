package org.woowacourse.lunchbot.slack.fragment.composition.text;

public class PlainText extends Text {
    public PlainText() {
    }

    public PlainText(String text) {
        super(TextType.PLAIN_TEXT, text);
    }
}

