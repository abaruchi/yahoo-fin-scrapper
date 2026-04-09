package com.yahoo_fin.scrapper.utils;

import com.yahoo_fin.scrapper.currency.Currency;
import com.yahoo_fin.scrapper.currency.USDExchangeRate;
import com.yahoo_fin.scrapper.currency.USDExchangeRateRepository;
import com.yahoo_fin.scrapper.types.MonetaryValue;

import java.time.LocalDateTime;
import java.util.Optional;

public class ExchangeRate  implements ExchangeRateCalculator{
    private final USDExchangeRateRepository exchangeRateRepository;

    public ExchangeRate(USDExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public MonetaryValue calculate(Currency sourceCurrency, Currency targetCurrency, MonetaryValue amount) {
        MonetaryValue sourceInUSD = calculateFromUSD(sourceCurrency, amount);
        Optional<USDExchangeRate> recentExchangeRate = exchangeRateRepository.findFirstByCurrencyOrderByTimestampDesc(targetCurrency);
        if (recentExchangeRate.isEmpty()) {
            return new MonetaryValue(0.0);
        }
        MonetaryValue targetInUSD = new MonetaryValue(recentExchangeRate.get().getRateInteger());
        return sourceInUSD.multiply(targetInUSD);
    }

    @Override
    public MonetaryValue calculateFromUSD(Currency target, MonetaryValue amount) {
        Optional<USDExchangeRate> recentExchangeRate = exchangeRateRepository.findFirstByCurrencyOrderByTimestampDesc(target);
        if (recentExchangeRate.isEmpty()) {
            return new MonetaryValue(0.0);
        }
        MonetaryValue usdExchangeRate = new MonetaryValue(recentExchangeRate.get().getRateInteger());
        return amount.divide(usdExchangeRate);
    }

    @Override
    public MonetaryValue calculateFromUSDWithDateTime(Currency target, MonetaryValue amount, LocalDateTime targetDate) {
        Optional<USDExchangeRate> recentExchangeRate = exchangeRateRepository.findFirstByCurrencyAndTimestampLessThanEqualOrderByTimestampDesc(target, targetDate);
        if (recentExchangeRate.isEmpty()) {
            return new MonetaryValue(0.0);
        }
        MonetaryValue usdExchangeRate = new MonetaryValue(recentExchangeRate.get().getRateInteger());
        return amount.divide(usdExchangeRate);
    }
}
