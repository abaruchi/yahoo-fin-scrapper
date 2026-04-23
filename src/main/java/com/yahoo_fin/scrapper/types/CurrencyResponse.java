package com.yahoo_fin.scrapper.types;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class CurrencyResponse {
    private final String currencyName;
    private final String currencyCode;
    private final MonetaryValue exchangeRateToUSD;
    private final LocalDateTime timestamp;
    private boolean isClose = false;

    public CurrencyResponse(String currencyName, String currencyCode, MonetaryValue exchangeRateToUSD, LocalDateTime timestamp) {
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.exchangeRateToUSD = exchangeRateToUSD;
        this.timestamp = timestamp;
    }

    public CurrencyResponse(String currencyName, String currencyCode, MonetaryValue exchangeRateToUSD) {
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.exchangeRateToUSD = exchangeRateToUSD;
        this.timestamp = LocalDateTime.now();
    }

    public static CurrencyResponse emptyCurrencyResponse() {
        return new CurrencyResponse("", "", new MonetaryValue(0));
    }

    public String getCurrencyName() {
        return currencyName;
    }
    public String getCurrencyCode() {
        return currencyCode;
    }
    public MonetaryValue getExchangeRateToUSD() {
        return exchangeRateToUSD;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean isClose) {
        this.isClose = isClose;
    }
}
