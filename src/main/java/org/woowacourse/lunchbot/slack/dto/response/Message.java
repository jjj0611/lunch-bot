package org.woowacourse.lunchbot.slack.dto.response;

public class Message {

    private String channel;
    private String text;

    public Message() {
    }

    public Message(String channel, String text) {
        this.channel = channel;
        this.text = text;
    }

    public String getChannel() {
        return channel;
    }

    public String getText() {
        return text;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "channel='" + channel + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
