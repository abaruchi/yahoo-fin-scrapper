package com.yahoo_fin.scrapper.utils.mappers;

import com.yahoo_fin.scrapper.types.MonetaryValue;
import com.yahoo_fin.scrapper.types.PriceResponse;
import com.yahoo_fin.scrapper.types.yahoo.ChartResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class YahooMapper {
    public enum InstrumentType {
        EQUITY("EQUITY"),
        CURRENCY("CURRENCY"),
        CRYPTOCURRENCY("CRYPTOCURRENCY");

        private final String value;

        InstrumentType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public List<PriceResponse> toPriceResponse(ChartResponse chartResponse){
        InstrumentType responseType = getInstrumentType(chartResponse.getChart().getResult().getFirst().getMeta().getInstrumentType());
        String assetCode = chartResponse.getChart().getResult().getFirst().getMeta().getShortName();
        List<PriceResponse> priceResponses = new ArrayList<>();

        if (responseType == InstrumentType.CURRENCY) {
            assetCode = getCurrencyCodeFromShortName(assetCode);
        }

        List<Long> timestamps = chartResponse.getChart().getResult().getFirst().getTimestamp();
        List<Double> prices = chartResponse.getChart().getResult().getFirst().getIndicators().getQuote().getFirst().getClose();

        int tsIndex = 0;
        for (Long timestamp : timestamps) {
            if (prices.get(tsIndex) == null) {
                tsIndex++;
                continue;
            }
            MonetaryValue price = new MonetaryValue(prices.get(tsIndex));
            String dataSource = "YAHOO_FINANCE";
            PriceResponse priceResponse = new PriceResponse(
                    dataSource,
                    price,
                    assetCode,
                    responseType.getValue(),
                    LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()));
            priceResponses.add(priceResponse);
            tsIndex++;
        }
        return priceResponses;
    }

    private InstrumentType getInstrumentType(String instrumentType) {
        return InstrumentType.valueOf(instrumentType);
    }

    private String getCurrencyCodeFromShortName(String shortName) {
        return shortName.split("/")[0];
    }
}
