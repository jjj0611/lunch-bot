package org.woowacourse.lunchbot.slack.fragment.element;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.woowacourse.lunchbot.slack.fragment.composition.text.PlainText;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ButtonElement extends Element {
    private PlainText text;
    private String actionId;
    private String style;

    public ButtonElement() {
        super(ElementType.BUTTON);
    }

    public ButtonElement(PlainText text, String actionId) {
        super(ElementType.BUTTON);
        this.text = text;
        this.actionId = actionId;
    }

    public ButtonElement(PlainText text, String actionId, String style) {
        super(ElementType.BUTTON);
        this.text = text;
        this.actionId = actionId;
        this.style = style;
    }

    public PlainText getText() {
        return text;
    }

    public String getActionId() {
        return actionId;
    }

    public String getStyle() {
        return style;
    }
}

