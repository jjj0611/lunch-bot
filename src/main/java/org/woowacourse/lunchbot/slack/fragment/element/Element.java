package org.woowacourse.lunchbot.slack.fragment.element;

public abstract class Element {
    private ElementType type;

    public Element() {
    }

    public Element(ElementType type) {
        this.type = type;
    }

    public ElementType getType() {
        return type;
    }
}

