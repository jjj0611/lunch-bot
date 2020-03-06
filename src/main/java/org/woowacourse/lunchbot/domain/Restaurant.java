package org.woowacourse.lunchbot.domain;

public class Restaurant {

    private long id;
    private String name;
    private String type;
    private String mainMenu;
    private int price;
    private String url;
    private String imageUrl;

    public Restaurant(
            long id,
            String name,
            String type,
            String mainMenu,
            int price,
            String url,
            String imageUrl
    ) {
//        Assert.hasLength(name, "name should be needed");
//        Assert.hasLength(type, "type should be needed");
//        Assert.hasLength(mainMenu, "mainMenu should be needed");
        this.id = id;
        this.name = name;
        this.type = type;
        this.mainMenu = mainMenu;
        this.price = price;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getMainMenu() {
        return mainMenu;
    }

    public int getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
