package com.yahoo_fin.scrapper.asset.crypto;
import jakarta.persistence.*;

import java.time.Clock;
import java.time.LocalDateTime;


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
    private LocalDateTime last_updated;

    public enum Currency {
        USD,
        AUD,
        BRL
    }

    public Crypto() {}
    public Crypto(String name, int price_integer, int price_decimal, String currency, Clock clock) {
        this.name = name;
        this.price_integer = price_integer;
        this.price_decimal = price_decimal;
        this.currency = Currency.valueOf(currency);
        this.setLastUpdated(clock);
    }

    public Crypto(String name, String price, String currency, Clock clock) {
        this.name = name;
        String[] priceParts = price.split("\\.");
        this.price_integer = Integer.parseInt(priceParts[0]);
        this.price_decimal = Integer.parseInt(priceParts[1]);
        this.currency = Currency.valueOf(currency);
        this.setLastUpdated(clock);
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

    public LocalDateTime getLastUpdated() {
        return last_updated;
    }

    public void setPrice(int price_integer, int price_decimal) {
        this.price_integer = price_integer;
        this.price_decimal = price_decimal;
    }

    public void setCurrency(String currency) {
        this.currency = Currency.valueOf(currency);
    }

    void setLastUpdated(Clock clock) {
        this.last_updated = LocalDateTime.now(clock);
    }
}
