package com.yahoo_fin.scrapper.currency;


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

    private double rate_integer;
    private double decimal_rate;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
}
