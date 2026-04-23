package com.yahoo_fin.scrapper.currency.service;

import com.yahoo_fin.scrapper.currency.Currency;
import com.yahoo_fin.scrapper.currency.CurrencyRepository;
import com.yahoo_fin.scrapper.currency.USDExchangeRate;
import com.yahoo_fin.scrapper.types.BackfillResponse;
import com.yahoo_fin.scrapper.types.records.CurrencyRefill;
import com.yahoo_fin.scrapper.utils.ExchangeRate;

import java.time.LocalDateTime;

public interface CurrencyBackfiller {
    USDExchangeRate getNewestRecord(Currency currency);
    CurrencyRefill runBackfill(LocalDateTime currentDateTime, LocalDateTime sinceDateTime, Currency currency);
}
