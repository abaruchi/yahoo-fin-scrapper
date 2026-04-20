package com.yahoo_fin.scrapper.utils;

import com.yahoo_fin.scrapper.asset.crypto.Crypto;
import com.yahoo_fin.scrapper.asset.stock.Stock;
import com.yahoo_fin.scrapper.currency.Currency;
import com.yahoo_fin.scrapper.types.CurrencyResponse;
import com.yahoo_fin.scrapper.types.PriceResponse;

import java.time.LocalDate;
import java.util.List;

public interface DataFetcher {
    public PriceResponse fetchCurrentStockPrice(Stock stock);
    public List<PriceResponse> fetchHistoricalStockPrices(Stock stock, LocalDate startDate, LocalDate endDate);
    public PriceResponse fetchCurrentCryptoPrice(Crypto crypto);
    public List<PriceResponse> fetchHistoricalCryptoPrices(Crypto crypto, LocalDate startDate, LocalDate endDate);
    public CurrencyResponse fetchCurrencyRateToUSD(Currency currency);
    public List<CurrencyResponse> fetchCurrencyRatesToUSD(Currency currency, LocalDate startDate, LocalDate endDate);
}
