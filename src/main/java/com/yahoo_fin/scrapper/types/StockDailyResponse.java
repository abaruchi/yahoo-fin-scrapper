package com.yahoo_fin.scrapper.types;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StockDailyResponse {
    private String code;
    private String companyName;
    private LocalDateTime timestamp;
    private MonetaryValue price;
    private boolean is_close;

    public StockDailyResponse(String code, String companyName, LocalDateTime timestamp, MonetaryValue price, boolean is_close) {
        this.code = code;
        this.companyName = companyName;
        this.timestamp = timestamp;
        this.price = price;
        this.is_close = is_close;
    }

    public StockDailyResponse(String code, String companyName, LocalDateTime timestamp, MonetaryValue price) {
        this.code = code;
        this.companyName = companyName;
        this.timestamp = timestamp;
        this.price = price;
        this.is_close = false;
    }
}
