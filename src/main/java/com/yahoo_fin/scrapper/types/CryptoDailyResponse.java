package com.yahoo_fin.scrapper.types;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CryptoDailyResponse {

    private String code;
    private String name;
    private MonetaryValue price;
    private boolean is_close;

    public CryptoDailyResponse(String code, String name, MonetaryValue price, boolean is_close) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.is_close = is_close;
    }

    public CryptoDailyResponse(String code, String name, MonetaryValue price) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.is_close = false;
    }
}
