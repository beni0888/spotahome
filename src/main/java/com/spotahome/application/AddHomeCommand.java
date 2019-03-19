package com.spotahome.application;

public final class AddHomeCommand {

    public final Long id;
    public final String title;
    public final String city;
    public final String url;
    public final String picture;


    public AddHomeCommand(Long id, String title, String city, String url, String picture) {
        this.id = id;
        this.title = title;
        this.city = city;
        this.url = url;
        this.picture = picture;
    }
}
