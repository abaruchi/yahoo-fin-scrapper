package com.yahoo_fin.scrapper.currency.service;

import com.yahoo_fin.scrapper.currency.Currency;
import com.yahoo_fin.scrapper.currency.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyListing {
    private final CurrencyRepository currencyRepository;

    public CurrencyListing(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getCurrencyList() {
        return (currencyRepository.findAllSortBy("code"));
    }

}
