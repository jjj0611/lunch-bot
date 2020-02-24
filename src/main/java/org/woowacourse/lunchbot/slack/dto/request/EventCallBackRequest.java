package org.woowacourse.lunchbot.slack.dto.request;

public class EventCallBackRequest {

    private Event event;

    public EventCallBackRequest() {

    }

    public EventCallBackRequest(Event event) {
        this.event = event;
    }

    public String getType() {
        return event.getType();
    }

    public String getChannel() {
        return event.getChannel();
    }

    public String getUserId() {
        return event.getUser();
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "EventCallBackRequest{" +
                "event=" + event +
                '}';
    }

    public static class Event {

        private String type;
        private String channel;
        private String user;

        public Event() {
        }

        public Event(String type, String channel, String user) {
            this.type = type;
            this.channel = channel;
            this.user = user;
        }

        public String getType() {
            return type;
        }

        public String getChannel() {
            return channel;
        }

        public String getUser() {
            return user;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public void setUser(String user) {
            this.user = user;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "type='" + type + '\'' +
                    ", channel='" + channel + '\'' +
                    ", user='" + user + '\'' +
                    '}';
        }
    }

}
