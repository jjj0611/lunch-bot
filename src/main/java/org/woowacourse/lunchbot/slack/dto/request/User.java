package org.woowacourse.lunchbot.slack.dto.request;

public class User {
    private String id;
    private String userName;
    private String name;
    private String teamId;

    public User(String id, String userName, String name, String teamId) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.teamId = teamId;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getTeamId() {
        return teamId;
    }
}
