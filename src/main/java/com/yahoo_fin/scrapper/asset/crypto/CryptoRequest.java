package com.yahoo_fin.scrapper.asset.crypto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CryptoRequest {
    private String name;
    private String price;
    private String currency;
}
