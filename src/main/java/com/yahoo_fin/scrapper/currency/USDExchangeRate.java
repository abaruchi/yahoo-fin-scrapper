package com.yahoo_fin.scrapper.currency;

import com.yahoo_fin.scrapper.types.PriceResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "usd_exchange_rate",
        indexes = {
                @Index(name = "idx_usd_exchange_rate_timestamp", columnList = "timestamp"),
                @Index(name = "idx_usd_exchange_rate_currency", columnList = "currency_id")
        }
)
public class USDExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double rateInteger;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    public USDExchangeRate() {}

    public USDExchangeRate(double rateInteger, LocalDateTime timestamp, Currency currency) {
        this.rateInteger = rateInteger;
        this.timestamp = timestamp;
        this.currency = currency;
    }

    public static USDExchangeRate nullUSDExchangeRate() {
        return new USDExchangeRate(0, LocalDateTime.MIN, null);
    }

    public static USDExchangeRate fromPriceResponse(PriceResponse priceResponse, Currency currency) {
        return new USDExchangeRate(
                priceResponse.getPrice().getNormalisedValue(),
                priceResponse.getTimestamp(),
                currency
        );
    }

}
