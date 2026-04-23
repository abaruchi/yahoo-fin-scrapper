package com.yahoo_fin.scrapper.currency.service;

import com.yahoo_fin.scrapper.currency.Currency;
import com.yahoo_fin.scrapper.currency.USDExchangeRate;
import com.yahoo_fin.scrapper.currency.USDExchangeRateRepository;
import com.yahoo_fin.scrapper.types.PriceResponse;
import com.yahoo_fin.scrapper.types.records.CurrencyRefill;
import com.yahoo_fin.scrapper.types.yahoo.ChartResponse;
import com.yahoo_fin.scrapper.utils.mappers.yahoo.YahooDataFetcher;
import com.yahoo_fin.scrapper.utils.mappers.yahoo.YahooMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class YahooBackFiller implements CurrencyBackfiller{

    private final YahooMapper yahooMapper;
    private final YahooDataFetcher yahooDataFetcher;
    private final USDExchangeRateRepository exchangeRateRepository;


    public YahooBackFiller(YahooMapper yahooMapper, YahooDataFetcher yahooDataFetcher, USDExchangeRateRepository exchangeRateRepository) {
        this.yahooMapper = yahooMapper;
        this.yahooDataFetcher = yahooDataFetcher;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public USDExchangeRate getNewestRecord(Currency currency) {
        Optional<USDExchangeRate> newestRecord = this.exchangeRateRepository.findFirstByCurrencyOrderByTimestampDesc(currency);
        return newestRecord.orElse(USDExchangeRate.nullUSDExchangeRate());
    }

    @Override
    public CurrencyRefill runBackfill(LocalDateTime currentDateTime, LocalDateTime sinceDateTime, Currency currency) {
        int cutOffMonths = 6;
        LocalDateTime sinceDateTarget = sinceDateTime;
        if (sinceDateTime.isBefore(currentDateTime.minusMonths(cutOffMonths))) {
            sinceDateTarget = currentDateTime.minusMonths(cutOffMonths);
        }
        String symbol = buildYahooSymbol(currency);
        ChartResponse chartResponse = yahooDataFetcher.getStockPricePreviousSixMonths(symbol);
        List<PriceResponse> exchangeRateResponsesToSave = yahooMapper.toPriceResponse(chartResponse);
        List<USDExchangeRate> exchangeRateList = convertPriceResponseToUSDExRate(exchangeRateResponsesToSave, currency);

        return new CurrencyRefill(
                currency.getCode(),
                saveExchangeRate(exchangeRateList, sinceDateTarget));
    }

    private int saveExchangeRate(List<USDExchangeRate> exchangeRateToSave, LocalDateTime dateLimit) {
        int savedRecords = 0;
        for (USDExchangeRate exchangeRate : exchangeRateToSave) {
            if (exchangeRate.getTimestamp().isBefore(dateLimit)) {
                continue;
            }
            exchangeRateRepository.save(exchangeRate);
            savedRecords++;
        }
        return savedRecords;
    }

    private String buildYahooSymbol(Currency currency) {
        return currency.getCode() + "USD=X";
    }

    private List<USDExchangeRate> convertPriceResponseToUSDExRate(List<PriceResponse> priceResponseList, Currency currency) {
        ArrayList<USDExchangeRate> exchangeRateList = new ArrayList<>();

        for (PriceResponse priceResponse : priceResponseList) {
            exchangeRateList.add(USDExchangeRate.fromPriceResponse(priceResponse, currency));
        }
        return exchangeRateList;
    }
}
