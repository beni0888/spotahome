package com.spotahome;

import com.spotahome.application.GetHomes;
import com.spotahome.domain.HomeRepository;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public GetHomes getHomes(HomeRepository homeRepository) {
        return new GetHomes(homeRepository);
    }
}
