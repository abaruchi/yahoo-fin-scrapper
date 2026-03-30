package com.yahoo_fin.scrapper.crypto;
import jakarta.persistence.*;

import java.time.Instant;


@Entity
@Table(
        name = "crypto",
        indexes = {
                @Index(name = "idx_crypto_name", columnList = "name"),
                @Index(name = "idx_crypto_currency", columnList = "currency")
        }
)
public class Crypto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price_integer;
    private int price_decimal;

    @Enumerated(EnumType.STRING)
    private Currency currency;
    private Instant last_updated;

    public enum Currency {
        USD,
        AUD,
        EUR,
        BRL
    }

    public Crypto() {}
    public Crypto(String name, int price_integer, int price_decimal, String currency) {
        this.name = name;
        this.price_integer = price_integer;
        this.price_decimal = price_decimal;
        this.currency = Currency.valueOf(currency);
        this.last_updated = Instant.now();
    }

    public Crypto(String name, String price, String currency) {
        this.name = name;
        String[] priceParts = price.split("\\.");
        this.price_integer = Integer.parseInt(priceParts[0]);
        this.price_decimal = Integer.parseInt(priceParts[1]);
        this.currency = Currency.valueOf(currency);
        this.last_updated = Instant.now();
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrice() {
        return price_integer + "." + price_decimal;
    }

    public String getCurrency() {
        return currency.toString();
    }

    public Instant getLastUpdated() {
        return last_updated;
    }

    public void setPrice(int price_integer, int price_decimal) {
        this.price_integer = price_integer;
        this.price_decimal = price_decimal;
    }

    public void setCurrency(String currency) {
        this.currency = Currency.valueOf(currency);
    }

    public void setLastUpdated(Instant last_updated) {
        this.last_updated = last_updated;
    }
}
