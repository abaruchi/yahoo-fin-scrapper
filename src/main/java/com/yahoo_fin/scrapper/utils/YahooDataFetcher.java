package com.yahoo_fin.scrapper.utils;

import com.yahoo_fin.scrapper.AppProperties;
import com.yahoo_fin.scrapper.asset.crypto.Crypto;
import com.yahoo_fin.scrapper.asset.crypto.CryptoRepository;
import com.yahoo_fin.scrapper.asset.stock.Stock;
import com.yahoo_fin.scrapper.asset.stock.StockRepository;
import com.yahoo_fin.scrapper.currency.Currency;
import com.yahoo_fin.scrapper.types.CurrencyResponse;
import com.yahoo_fin.scrapper.types.MonetaryValue;
import com.yahoo_fin.scrapper.types.PriceResponse;
import org.springframework.stereotype.Component;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.fx.FxQuote;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class YahooDataFetcher implements DataFetcher {

    private final CryptoRepository cryptoRepository;
    private final StockRepository stockRepository;
    private final AppProperties appProperties;

    public YahooDataFetcher(CryptoRepository cryptoRepository, StockRepository stockRepository, AppProperties appProperties) {
        this.cryptoRepository = cryptoRepository;
        this.stockRepository = stockRepository;
        this.appProperties = appProperties;
    }

    @Override
    public PriceResponse fetchCurrentStockPrice(Stock stock) {
        return null;
    }

    @Override
    public List<PriceResponse> fetchHistoricalStockPrices(Stock stock, LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public PriceResponse fetchCurrentCryptoPrice(Crypto crypto) {
        return null;
    }

    @Override
    public List<PriceResponse> fetchHistoricalCryptoPrices(Crypto crypto, LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public CurrencyResponse fetchCurrencyRateToUSD(Currency currency) {
        String yahooFinanceCode = getYahooFinanceCurrencyParameter(currency.getCode());
        FxQuote fxQuote;
        MonetaryValue currentExchangeRate;
        try {
            fxQuote = YahooFinance.getFx(yahooFinanceCode);
            currentExchangeRate = new MonetaryValue(String.valueOf(fxQuote.getPrice()));
        } catch (IOException _) {
            return CurrencyResponse.emptyCurrencyResponse();
        }

        return new CurrencyResponse(
                currency.getName(),
                currency.getCode(),
                currentExchangeRate
        );
    }

    @Override
    public List<CurrencyResponse> fetchCurrencyRatesToUSD(Currency currency, LocalDate startDate, LocalDate endDate) {
        Calendar startDateCalendar = Calendar.getInstance();
        Calendar endDateCalendar = Calendar.getInstance();
        yahoofinance.Stock currencyHist;
        List<HistoricalQuote> currencyHistQuotes;
        List<CurrencyResponse> currencyResponses = new ArrayList<>();;

        startDateCalendar.set(startDate.getYear(), startDate.getMonthValue() - 1, startDate.getDayOfMonth());
        endDateCalendar.set(endDate.getYear(), endDate.getMonthValue() - 1, endDate.getDayOfMonth());


        try {
            currencyHist = YahooFinance.get(
                    getYahooFinanceCurrencyParameter(currency.getCode()),
                    startDateCalendar,
                    endDateCalendar,
                    Interval.DAILY);
            currencyHistQuotes = currencyHist.getHistory();
        } catch (IOException e) {
            return List.of(CurrencyResponse.emptyCurrencyResponse());
        }

        for (HistoricalQuote quote : currencyHistQuotes) {
            MonetaryValue currentExchangeRate;
            currentExchangeRate = new MonetaryValue(quote.getClose().doubleValue());

            CurrencyResponse currencyResponse = new CurrencyResponse(
                    currency.getName(),
                    currency.getCode(),
                    currentExchangeRate,
                    LocalDateTime.ofInstant(quote.getDate().toInstant(), ZoneId.systemDefault())
            );
            currencyResponses.add(currencyResponse);
        }

        return currencyResponses;
    }

    private String getYahooFinanceCurrencyParameter(String currencyCode) {
        return currencyCode.toUpperCase() + "USD=X";
    }
}
