package com.spotahome.application;

import com.spotahome.domain.Home;
import com.spotahome.domain.HomeRepository;

public class AddHome {

    HomeRepository repository;

    public AddHome(HomeRepository repository) {
        this.repository = repository;
    }

    public Home execute(AddHomeCommand command) {
        Home home = Home.create(command.id, command.title, command.city, command.url, command.picture);
        repository.save(home);
        return home;
    }
}
