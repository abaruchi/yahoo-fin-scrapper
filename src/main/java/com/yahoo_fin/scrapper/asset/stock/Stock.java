package com.yahoo_fin.scrapper.asset.stock;


import com.yahoo_fin.scrapper.market.Market;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String companyTicker;

    @OneToOne(mappedBy = "stock", cascade = CascadeType.ALL)
    private Market market;

    @OneToMany(mappedBy = "stock")
    private List<StockPrice> stockPrices = new ArrayList<>();

    public Stock() {}
    public Stock(String companyName, String companyTicker, Market market) {
        this.companyName = companyName;
        this.companyTicker = companyTicker;
        this.market = market;
    }
}
