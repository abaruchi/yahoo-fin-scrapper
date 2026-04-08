package com.yahoo_fin.scrapper.asset.crypto;
import com.yahoo_fin.scrapper.types.MonetaryValue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Clock;
import java.time.LocalDateTime;

@Getter
@Setter
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
    private double price;

    @Enumerated(EnumType.STRING)
    private Currency currency;
    private LocalDateTime last_updated;

    public enum Currency {
        USD,
        AUD,
        BRL
    }

    public Crypto() {}

    public Crypto(String name, MonetaryValue price, String currency, Clock clock) {
        this.name = name;
        this.price = price.getValue();
        this.currency = Currency.valueOf(currency);
        this.setLastUpdated(clock);
    }

    public MonetaryValue getPrice() {
        return new MonetaryValue(price);
    }

    public String getCurrency() {
        return currency.toString();
    }

    public LocalDateTime getLastUpdated() {
        return last_updated;
    }

    public void setPrice(MonetaryValue price) {
        this.price = price.getValue();
    }

    public void setCurrency(String currency) {
        this.currency = Currency.valueOf(currency);
    }

    public void setLastUpdated(Clock clock) {
        this.last_updated = LocalDateTime.now(clock);
    }
}
