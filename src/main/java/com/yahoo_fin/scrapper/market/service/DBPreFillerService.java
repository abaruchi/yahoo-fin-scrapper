package com.yahoo_fin.scrapper.market.service;

import com.yahoo_fin.scrapper.AppProperties;
import com.yahoo_fin.scrapper.market.Country;
import com.yahoo_fin.scrapper.market.CountryRepository;
import com.yahoo_fin.scrapper.market.Market;
import com.yahoo_fin.scrapper.market.MarketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBPreFillerService {

    private final CountryRepository countryRepository;
    private final MarketRepository marketRepository;
    private final AppProperties appProperties;

    public DBPreFillerService(CountryRepository countryRepository, MarketRepository marketRepository, AppProperties appProperties) {
        this.countryRepository = countryRepository;
        this.marketRepository = marketRepository;
        this.appProperties = appProperties;
    }

    public void populateCountryAndMarketTables() {
        List<AppProperties.Market> markets = appProperties.getMarkets();

        for (AppProperties.Market market : markets) {
            String countryCode = market.getCountryCode();
            String countryName = market.getCountryName();
            String marketTicker = market.getMarketTicker();
            String marketName = market.getMarketName();

            if (countryRepository.findByCodeEqualsIgnoreCase(countryCode).isEmpty()) {
                Country country = new Country(countryName.toUpperCase(), countryCode.toUpperCase());
                countryRepository.save(country);
                addMarket(country, marketTicker, marketName);
            }
        }
    }

    private void addMarket(Country country, String marketTicker, String marketName) {
        if (marketRepository.findByCountry(country).isEmpty()) {
            Market market = new Market(marketName, marketTicker, country);
            marketRepository.save(market);
        }
    }
}
