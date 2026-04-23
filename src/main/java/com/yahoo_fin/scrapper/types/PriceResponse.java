package com.yahoo_fin.scrapper.types;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PriceResponse {
    private final String dataSource;
    private final MonetaryValue price;
    private final String assetCode;
    private final String assetName;
    private final LocalDateTime timestamp;
    private boolean isClose = false;

    public PriceResponse(String dataSource, MonetaryValue price, String assetCode, String assetName, LocalDateTime timestamp) {
        this.dataSource = dataSource;
        this.price = price;
        this.assetCode = assetCode;
        this.assetName = assetName;
        this.timestamp = timestamp;
    }

    public PriceResponse(String dataSource, MonetaryValue price, String assetCode, String assetName) {
        this.dataSource = dataSource;
        this.price = price;
        this.assetCode = assetCode;
        this.assetName = assetName;
        this.timestamp = LocalDateTime.now();
    }

    public void setClose(boolean isClose) {
        this.isClose = isClose;
    }
}
