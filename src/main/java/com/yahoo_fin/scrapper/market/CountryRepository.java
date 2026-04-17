package com.yahoo_fin.scrapper.market;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByNameEqualsIgnoreCase(String name);
    Optional<Country> findByCodeEqualsIgnoreCase(String code);

}
