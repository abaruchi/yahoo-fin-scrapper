package com.yahoo_fin.scrapper.asset.crypto;

public class CryptoRequest {
    private String name;
    private String price;
    private String currency;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
