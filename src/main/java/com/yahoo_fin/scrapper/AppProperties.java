package com.yahoo_fin.scrapper;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final List<Market> markets;
    private final List<String> cryptos;

    public AppProperties(List<Market> markets, List<String> cryptos) {
        this.markets = markets;
        this.cryptos = cryptos;
    }

    public static class Market {
        private String countryName;
        private String countryCode;
        private List<String> stocks;
        private String marketName;
        private String marketTicker;
        private String currency;

        public String getCountryName() {
            return this.countryName;
        }

        public String getCountryCode() {
            return this.countryCode;
        }

        public List<String> getStocks() {
            return this.stocks;
        }

        public String getMarketName() {
            return this.marketName;
        }

        public String getMarketTicker() {return this.marketTicker;}

        public String getCurrency() {
            return this.currency;
        }

        public void setCountryName(String countryName) { this.countryName = countryName; }

        public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

        public void setStocks(List<String> stocks) {
            this.stocks = stocks;
        }

        public void setMarketName(String marketName) {
            this.marketName = marketName;
        }

        public void setMarketTicker(String marketTicker) {this.marketTicker = marketTicker;}

        public void setCurrency(String currency) {
            this.currency = currency;
        }
    }

    public List<Market> getMarkets() {
        return this.markets;
    }

    public List<String> getCryptos() {
        return this.cryptos;
    }
}
