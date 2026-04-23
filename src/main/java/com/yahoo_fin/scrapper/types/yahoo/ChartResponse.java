package com.yahoo_fin.scrapper.types.yahoo;

import lombok.Data;

import java.util.List;

@Data
public class ChartResponse {
    private Chart chart;

    @Data
    public static class Chart {
        private List<Result> result;
    }

    @Data
    public static class Meta {
        private String longName;
        private String shortName;
        private String instrumentType;
        private String symbol;
    }

    @Data
    public static class Result {
        private Meta meta;
        private List<Long> timestamp;
        private Indicators indicators;
    }

    @Data
    public static class Indicators {
        private List<Quote> quote;
    }

    @Data
    public static class Quote {
        private List<Double> close;
    }
}