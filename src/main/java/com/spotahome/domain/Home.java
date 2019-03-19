package com.spotahome.domain;

import java.net.URI;

public class Home {

    private Long id;
    private String title;
    private String city;
    private URI url;
    private URI picture;

    private Home(Long id, String title, String city, URI url, URI picture) {
        this.id = id;
        this.title = title;
        this.city = city;
        this.url = url;
        this.picture = picture;
    }

    public static Home create(Long id, String title, String city, String url, String picture) {
        return new Home(id, title, city, parseUrl(url), parseUrl(picture));
    }

    private static URI parseUrl(String url) {
        try {
            return URI.create(url);
        } catch (Exception e) {
            return null;
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCity() {
        return city;
    }

    public URI getUrl() {
        return url;
    }

    public URI getPicture() {
        return picture;
    }
}
