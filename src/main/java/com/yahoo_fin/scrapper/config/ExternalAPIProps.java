package com.yahoo_fin.scrapper.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="external.api")
public class ExternalAPIProps {
    private String baseUrl;
    private String key;
    private int timeoutSec = 10;
    private int retryCount = 3;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getKey() {
        return key;
    }

    public int getTimeoutSec() {
        return timeoutSec;
    }

    public int getRetryCount() {
        return retryCount;
    }
}
