package com.yahoo_fin.scrapper.utils;

import com.yahoo_fin.scrapper.currency.Currency;
import com.yahoo_fin.scrapper.types.MonetaryValue;

import java.time.LocalDateTime;

public interface ExchangeRateCalculator {
    MonetaryValue calculate(Currency source, Currency target, MonetaryValue amount);
    MonetaryValue calculateFromUSD(Currency target, MonetaryValue amount);
    MonetaryValue calculateFromUSDWithDateTime(Currency target, MonetaryValue amount,LocalDateTime targetDate);
}
