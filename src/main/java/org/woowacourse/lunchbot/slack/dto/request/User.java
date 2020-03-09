package org.woowacourse.lunchbot.slack.dto.request;

public class User {
    private final String id;
    private final String userName;
    private final String name;
    private final String teamId;

    public User() {
        this.id = "";
        this.userName = "";
        this.name = "";
        this.teamId = "";
    }

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
