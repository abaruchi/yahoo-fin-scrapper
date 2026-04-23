package com.yahoo_fin.scrapper.currency.service;

import com.yahoo_fin.scrapper.currency.Currency;
import com.yahoo_fin.scrapper.currency.CurrencyRepository;
import com.yahoo_fin.scrapper.currency.USDExchangeRate;
import com.yahoo_fin.scrapper.currency.USDExchangeRateRepository;
import com.yahoo_fin.scrapper.market.Country;
import com.yahoo_fin.scrapper.market.CountryRepository;
import com.yahoo_fin.scrapper.types.yahoo.ChartResponse;
import com.yahoo_fin.scrapper.utils.mappers.yahoo.FakeYahooDataFetcher;
import com.yahoo_fin.scrapper.utils.mappers.yahoo.YahooMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class YahooBackFillerTest {

    @Autowired
    private YahooMapper yahooMapper;

    @Autowired
    private USDExchangeRateRepository exchangeRateRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CountryRepository countryRepository;

    Country fakeCountry01 = new Country("FakeCountry 01", "FKC01");
    Country fakeCountry02 = new Country("FakeCountry 02", "FKC02");

    LocalDateTime currentDate = LocalDateTime.of(2026, 10, 15, 10, 0);

    @BeforeEach
    void setUp() {
        countryRepository.save(fakeCountry01);
        countryRepository.save(fakeCountry02);
    }

    @Test
    void getNewestUSDExchangeRateFromExistingCurrency() {
        Currency currency_fk01 = new Currency("FakeCountry 01", "FKC01", fakeCountry01);
        Currency currency_fk02 = new Currency("FakeCountry 02", "FKC02", fakeCountry02);
        currencyRepository.save(currency_fk01);
        currencyRepository.save(currency_fk02);

        List<USDExchangeRate> exchangeRateList = List.of(
                new USDExchangeRate(1500, currentDate.minusDays(9), currency_fk01),
                new USDExchangeRate(1420, currentDate.minusDays(2), currency_fk01),
                new USDExchangeRate(1425, currentDate.minusDays(4), currency_fk01),
                new USDExchangeRate(1300, currentDate.minusDays(20), currency_fk01),
                new USDExchangeRate(150, currentDate.minusDays(11), currency_fk02),
                new USDExchangeRate(160, currentDate.minusDays(34), currency_fk02),
                new USDExchangeRate(170, currentDate.minusDays(1), currency_fk02)
        );
        exchangeRateRepository.saveAll(exchangeRateList);
        ChartResponse emptyChartResponse = new ChartResponse();
        FakeYahooDataFetcher fakeYahooDataFetcher = new FakeYahooDataFetcher(emptyChartResponse);

        YahooBackFiller yahooBackFiller = new YahooBackFiller(yahooMapper, fakeYahooDataFetcher, exchangeRateRepository);
        USDExchangeRate newestRecord = yahooBackFiller.getNewestRecord(currency_fk01);
        assertEquals(currentDate.minusDays(2), newestRecord.getTimestamp());

        newestRecord = yahooBackFiller.getNewestRecord(currency_fk02);
        assertEquals(currentDate.minusDays(1), newestRecord.getTimestamp());
    }

    @Test
    void getNewestUSDExchangeRateFromNonExistingCurrency() {
        Currency currency_fk01 = new Currency("FakeCountry 01", "FKC01", fakeCountry01);
        currencyRepository.save(currency_fk01);

        ChartResponse emptyChartResponse = new ChartResponse();
        FakeYahooDataFetcher fakeYahooDataFetcher = new FakeYahooDataFetcher(emptyChartResponse);
        YahooBackFiller yahooBackFiller = new YahooBackFiller(yahooMapper, fakeYahooDataFetcher, exchangeRateRepository);
        USDExchangeRate newestRecord = yahooBackFiller.getNewestRecord(currency_fk01);
        assertEquals(LocalDateTime.MIN, newestRecord.getTimestamp());
    }
}