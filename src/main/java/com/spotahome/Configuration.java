package com.spotahome;

import com.spotahome.application.AddHome;
import com.spotahome.application.GetHomes;
import com.spotahome.domain.HomeRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public GetHomes getHomes(HomeRepository homeRepository) {
        return new GetHomes(homeRepository);
    }

    @Bean
    public AddHome addHome(HomeRepository homeRepository) {
        return new AddHome(homeRepository);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
