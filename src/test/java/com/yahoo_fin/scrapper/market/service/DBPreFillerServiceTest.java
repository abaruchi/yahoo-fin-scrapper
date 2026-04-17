package com.yahoo_fin.scrapper.market.service;

import com.yahoo_fin.scrapper.AppProperties;
import com.yahoo_fin.scrapper.market.Country;
import com.yahoo_fin.scrapper.market.CountryRepository;
import com.yahoo_fin.scrapper.market.Market;
import com.yahoo_fin.scrapper.market.MarketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(
        properties = {
                "app.markets[0].country-name=FAKE Country 00",
                "app.markets[0].country-code=FKC00",
                "app.markets[0].market-name=FKMKT00",
                "app.markets[0].market-ticker=FK0",

                "app.markets[1].country-name=FAKE Country 01",
                "app.markets[1].country-code=FKC01",
                "app.markets[1].market-name=FKMKT01",
                "app.markets[1].market-ticker=FK1",
        }
)
class DBPreFillerServiceTest {

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private AppProperties appProperties;

    @Test
    void populateCountryAndMarketTableWithEmptyData() {
        DBPreFillerService dbPreFillerService = new DBPreFillerService(countryRepository, marketRepository, appProperties);
        dbPreFillerService.populateCountryAndMarketTables();

        List<Country> countries = countryRepository.findAll();
        List<Market> markets = marketRepository.findAll();

        assertEquals(2, countries.size());
        assertEquals(2, markets.size());
    }

    @Test
    void populateCountryAndMarketTableWithExistingData() {
        DBPreFillerService dbPreFillerService = new DBPreFillerService(countryRepository, marketRepository, appProperties);
        dbPreFillerService.populateCountryAndMarketTables();
        dbPreFillerService.populateCountryAndMarketTables();

        List<Country> countries = countryRepository.findAll();
        List<Market> markets = marketRepository.findAll();

        assertEquals(2, countries.size());
        assertEquals(2, markets.size());
    }
}