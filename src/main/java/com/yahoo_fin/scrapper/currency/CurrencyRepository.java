package com.yahoo_fin.scrapper.currency;

import com.yahoo_fin.scrapper.market.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByNameEqualsIgnoreCase(String name);
    Currency findByCodeEqualsIgnoreCase(String code);
    Currency findByCountry(Country country);
    List<Currency> findAllSortBy(String field);
}
