package com.yahoo_fin.scrapper.market;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Long> {
    Market findByNameEqualsIgnoreCase(String name);
    Market findByCodeEqualsIgnoreCase(String code);
    Market findByCountry(Country country);
}
