package com.yahoo_fin.scrapper.utils.mappers.yahoo;

import com.yahoo_fin.scrapper.types.yahoo.ChartResponse;

public class FakeYahooDataFetcher implements YahooDataFetcher {

    private final ChartResponse response;

    public FakeYahooDataFetcher(ChartResponse response) {
        this.response = response;
    }

    @Override
    public ChartResponse getStockPriceCurrent(String symbol) {
        return response;
    }

    @Override
    public ChartResponse getStockPricePreviousSixMonths(String symbol) {
        return response;
    }

    @Override
    public ChartResponse getStockPriceFromToDate(String symbol, String startDate, String endDate) {
        return response;
    }
}