package com.yahoo_fin.scrapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final List<Market> markets;
    private final List<String> cryptos;

    public AppProperties(List<Market> markets, List<String> cryptos) {
        this.markets = markets;
        this.cryptos = cryptos;
    }

    @Getter
    @Setter
    public static class Market {
        private String countryName;
        private String countryCode;
        private List<String> stocks;
        private String marketName;
        private String marketTicker;
        private String currency;
    }

}
