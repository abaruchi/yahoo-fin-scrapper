package com.yahoo_fin.scrapper.market.service;

import com.yahoo_fin.scrapper.AppProperties;
import com.yahoo_fin.scrapper.currency.Currency;
import com.yahoo_fin.scrapper.currency.CurrencyRepository;
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
    private final CurrencyRepository currencyRepository;

    public DBPreFillerService(CountryRepository countryRepository, MarketRepository marketRepository, AppProperties appProperties, CurrencyRepository currencyRepository) {
        this.countryRepository = countryRepository;
        this.marketRepository = marketRepository;
        this.appProperties = appProperties;
        this.currencyRepository = currencyRepository;
    }

    public void populateCountryAndMarketTables() {
        List<AppProperties.Market> markets = appProperties.getMarkets();

        for (AppProperties.Market market : markets) {
            String countryCode = market.getCountryCode();
            String countryName = market.getCountryName();
            String marketTicker = market.getMarketTicker();
            String marketName = market.getMarketName();
            String currency = market.getCurrency();

            if (countryRepository.findByCodeEqualsIgnoreCase(countryCode).isEmpty()) {
                Country country = new Country(countryName.toUpperCase(), countryCode.toUpperCase());
                countryRepository.save(country);
                addMarket(country, marketTicker, marketName);
                addCurrency(country, countryName, currency);
            }
        }
    }

    private void addMarket(Country country, String marketTicker, String marketName) {
        if (marketRepository.findByCountry(country).isEmpty()) {
            Market market = new Market(marketName, marketTicker, country);
            marketRepository.save(market);
        }
    }

    private void addCurrency(Country country, String currencyName, String currencyCode) {
        Currency currencyByCountry = currencyRepository.findByCountry(country);
        if (currencyByCountry == null) {
            Currency currency = new Currency(currencyName, currencyCode, country);
            currencyRepository.save(currency);
        }
    }
}
