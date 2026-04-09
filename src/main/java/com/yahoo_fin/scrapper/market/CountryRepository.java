package com.yahoo_fin.scrapper.market;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByNameEqualsIgnoreCase(String name);
    Country findByCodeEqualsIgnoreCase(String code);
}
