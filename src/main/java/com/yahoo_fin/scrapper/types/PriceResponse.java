package com.yahoo_fin.scrapper.types;

import java.time.LocalDateTime;

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

    public String getDataSource() { return dataSource; }
    public MonetaryValue getPrice() { return price; }
    public String getAssetCode() { return assetCode; }
    public String getAssetName() { return assetName; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean isClose() { return isClose; }

    public void setClose(boolean isClose) {
        this.isClose = isClose;
    }
}
