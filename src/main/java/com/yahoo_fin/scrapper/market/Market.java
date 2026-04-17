package com.yahoo_fin.scrapper.market;


import com.yahoo_fin.scrapper.asset.stock.Stock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "market"
)
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    public Market(String name, String code, Country country) {
        this.name = name;
        this.code = code;
        this.country = country;
    }

    public Market(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Market() {}
}
