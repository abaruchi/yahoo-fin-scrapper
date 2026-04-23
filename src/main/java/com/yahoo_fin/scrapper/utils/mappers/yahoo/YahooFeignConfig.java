package com.yahoo_fin.scrapper.utils.mappers.yahoo;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YahooFeignConfig {
    @Value("${app.rapid-api-key}")
    private String apiKey;

    @Value("${app.rapid-api-host}")
    private String host;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("x-rapidapi-key", apiKey);
            requestTemplate.header("x-rapidapi-host", host);
            requestTemplate.header("Content-Type", "application/json");
        };
    }
}
