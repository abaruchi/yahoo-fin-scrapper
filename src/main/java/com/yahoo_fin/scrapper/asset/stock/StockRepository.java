package com.yahoo_fin.scrapper.asset.stock;

import com.yahoo_fin.scrapper.market.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository  extends JpaRepository<Stock, Long> {
    Stock findByCompanyNameEqualsIgnoreCase(String name);
    Stock findByCodeEqualsIgnoreCase(String symbol);
    Stock findByMarket(Market market);
}
