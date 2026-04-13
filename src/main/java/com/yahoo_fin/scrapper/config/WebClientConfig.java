package com.yahoo_fin.scrapper.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(ExternalAPIProps props) {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(props.getTimeoutSec()));

        WebClient.Builder builder = WebClient.builder()
                .baseUrl(props.getBaseUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient));

        if (props.getRetryCount() > 0) {
            builder.filter((request, next) -> next.exchange(request)
                    .retryWhen(Retry.fixedDelay(props.getRetryCount(), Duration.ofSeconds(1))));
        }
        return builder.build();
    }
}
