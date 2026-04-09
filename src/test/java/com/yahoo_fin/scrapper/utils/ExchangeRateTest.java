package com.yahoo_fin.scrapper.utils;

import com.yahoo_fin.scrapper.currency.Currency;
import com.yahoo_fin.scrapper.currency.CurrencyRepository;
import com.yahoo_fin.scrapper.currency.USDExchangeRate;
import com.yahoo_fin.scrapper.currency.USDExchangeRateRepository;
import com.yahoo_fin.scrapper.market.Country;
import com.yahoo_fin.scrapper.market.CountryRepository;
import com.yahoo_fin.scrapper.types.MonetaryValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ExchangeRateTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private USDExchangeRateRepository exchangeRateRepository;

    @Autowired
    private CountryRepository countryRepository;

    ExchangeRate exchangeRate;
    LocalDateTime dayOne = LocalDateTime.of(2022, 1, 1, 0, 0);
    LocalDateTime dayTwo = LocalDateTime.of(2022, 1, 2, 0, 0);
    LocalDateTime dayThree = LocalDateTime.of(2022, 1, 3, 0, 0);

    Country usCountry = new Country("United States", "USD");
    Country ukCountry = new Country("United Kingdom", "UK");
    Country brCountry = new Country("Brazil", "BR");

    Currency usdCurrency = new Currency("US Dollar", "USD", usCountry);
    Currency ukCurrency = new Currency("Pound Sterling", "UK", ukCountry);
    Currency brCurrency = new Currency("Real", "BRL", brCountry);

    USDExchangeRate usdExchangeRateDayOne = new USDExchangeRate(1.20, dayOne, ukCurrency);
    USDExchangeRate usdExchangeRateDayTwo = new USDExchangeRate(1.25, dayTwo, ukCurrency);
    USDExchangeRate usdExchangeRateDayThree = new USDExchangeRate(1.30, dayThree, ukCurrency);
    USDExchangeRate brlExchangeRateDayOne = new USDExchangeRate(5.20, dayOne, brCurrency);
    USDExchangeRate brlExchangeRateDayTwo = new USDExchangeRate(5.25, dayTwo, brCurrency);
    USDExchangeRate brlExchangeRateDayThree = new USDExchangeRate(5.30, dayThree, brCurrency);

    @BeforeEach
    void setUp() {
        countryRepository.save(usCountry);
        countryRepository.save(ukCountry);
        countryRepository.save(brCountry);

        currencyRepository.save(usdCurrency);
        currencyRepository.save(ukCurrency);
        currencyRepository.save(brCurrency);

        exchangeRateRepository.save(usdExchangeRateDayOne);
        exchangeRateRepository.save(usdExchangeRateDayTwo);
        exchangeRateRepository.save(usdExchangeRateDayThree);
        exchangeRateRepository.save(brlExchangeRateDayOne);
        exchangeRateRepository.save(brlExchangeRateDayTwo);
        exchangeRateRepository.save(brlExchangeRateDayThree);

        exchangeRate = new ExchangeRate(exchangeRateRepository);
    }

    @Test
    void calculateFromUSD() {
        MonetaryValue amount = new MonetaryValue(100.0);
        MonetaryValue convertedBRToUSD = exchangeRate.calculateFromUSD(brCurrency, amount);
        assertEquals(1886, convertedBRToUSD.getNormalisedValue());
        assertEquals(100 / brlExchangeRateDayThree.getRateInteger(), convertedBRToUSD.getRawValue());

        amount = new MonetaryValue(100.0);
        MonetaryValue convertedUKToUSD = exchangeRate.calculateFromUSD(ukCurrency, amount);
        assertEquals(7692, convertedUKToUSD.getNormalisedValue());
        assertEquals(100 / usdExchangeRateDayThree.getRateInteger(), convertedUKToUSD.getRawValue());
    }

    @Test
    void testCalculateBRLToUK() {
        MonetaryValue amount = new MonetaryValue(100.0);
        MonetaryValue convertedBRLToUK = exchangeRate.calculate(brCurrency, ukCurrency, amount);
        assertEquals(2451, convertedBRLToUK.getNormalisedValue());
        assertEquals(18.86 * usdExchangeRateDayThree.getRateInteger(), convertedBRLToUK.getRawValue());
    }

    @Test
    void testCalculateUSDToBRLInDayTwo() {
        MonetaryValue amount = new MonetaryValue(100.0);
        MonetaryValue convertedUSDToBRL = exchangeRate.calculateFromUSDWithDateTime(brCurrency, amount, dayTwo);
        assertEquals(1904, convertedUSDToBRL.getNormalisedValue());
        assertEquals(100 / brlExchangeRateDayTwo.getRateInteger(), convertedUSDToBRL.getRawValue());
    }

    @Test
    void testCalculateUSDToBRLInDayOne() {
        MonetaryValue amount = new MonetaryValue(100.0);
        MonetaryValue convertedUSDToBRL = exchangeRate.calculateFromUSDWithDateTime(brCurrency, amount, dayOne);
        assertEquals(1923, convertedUSDToBRL.getNormalisedValue());
        assertEquals(100 / brlExchangeRateDayOne.getRateInteger(), convertedUSDToBRL.getRawValue());
    }
}