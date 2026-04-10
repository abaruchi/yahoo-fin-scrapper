package com.yahoo_fin.scrapper.asset.stock;


import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
    StockPrice findByStockAndTimestamp(Stock stock, java.time.LocalDateTime timestamp);
    StockPrice findByStock(Stock stock);
    StockPrice findByStockAndTimestampLessThanEqualOrderByTimestampDesc(Stock stock, java.time.LocalDateTime timestamp);
}
