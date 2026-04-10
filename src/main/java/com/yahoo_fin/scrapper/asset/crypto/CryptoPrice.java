package com.yahoo_fin.scrapper.asset.crypto;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "crypto_price")
public class CryptoPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double priceUSD;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "crypto_id")
    private Crypto crypto;
}
