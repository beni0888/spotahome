package com.spotahome.infrastructure.persistence.jpa;

import com.spotahome.domain.Home;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URI;

@Entity
public class HomeJpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String city;

    private String url;

    private String picture;

    public Long getId() {
        return id;
    }

    private HomeJpaEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public HomeJpaEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCity() {
        return city;
    }

    public HomeJpaEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public HomeJpaEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public HomeJpaEntity setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public static HomeJpaEntity fromDomain(Home home) {
        HomeJpaEntity homeEntity = new HomeJpaEntity();
        homeEntity.setId(home.getId()).setTitle(home.getTitle()).setCity(home.getCity())
                .setUrl(home.getUrl().toString()).setPicture(home.getPicture().toString());
        return homeEntity;
    }

    public Home toDomain() {
        return Home.create(getId(), getTitle(), getCity(), URI.create(getUrl()), URI.create(getPicture()));
    }
}
