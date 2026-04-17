package com.yahoo_fin.scrapper.market;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market, Long> {
    Optional<Market> findByNameEqualsIgnoreCase(String name);
    Optional<Market> findByCodeEqualsIgnoreCase(String code);
    Optional<Market> findByCountry(Country country);
}
