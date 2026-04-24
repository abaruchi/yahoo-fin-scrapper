package com.yahoo_fin.scrapper.currency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface USDExchangeRateRepository extends JpaRepository<USDExchangeRate, Long> {
    Optional<USDExchangeRate> findFirstByCurrencyAndTimestampLessThanEqualOrderByTimestampDesc(
            Currency currency,
            LocalDateTime timestamp
    );
    Optional<USDExchangeRate> findFirstByCurrencyOrderByTimestampDesc(Currency currency);
    Optional<USDExchangeRate> findFirstByCurrencyAndTimestamp(Currency currency, LocalDateTime timestamp);
    Optional<USDExchangeRate> findMaxByCurrency(Currency currency);
    Integer countByCurrency(Currency currency);
    Integer countByCurrencyAndTimestampLessThanEqual(Currency currency, LocalDateTime timestamp);
    Integer countByCurrencyAndTimestamp(Currency currency, LocalDateTime timestamp);
    Integer countByCurrencyAndTimestampGreaterThan(Currency currency, LocalDateTime timestamp);
}
