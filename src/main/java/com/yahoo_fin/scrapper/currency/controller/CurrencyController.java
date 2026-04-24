package com.yahoo_fin.scrapper.currency.controller;

import com.yahoo_fin.scrapper.currency.Currency;
import com.yahoo_fin.scrapper.currency.USDExchangeRate;
import com.yahoo_fin.scrapper.currency.service.CurrencyListing;
import com.yahoo_fin.scrapper.currency.service.YahooBackFiller;
import com.yahoo_fin.scrapper.types.BackfillResponse;
import com.yahoo_fin.scrapper.types.records.CurrencyRefill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.time.ZoneOffset.UTC;

@RestController
public class CurrencyController {
    private static final Logger logger = Logger.getLogger(CurrencyController.class.getName());

    private final String currencyRootPath = "/currency";
    private final YahooBackFiller yahooBackFiller;
    private final CurrencyListing currencyListing;

    public CurrencyController(YahooBackFiller yahooBackFiller, CurrencyListing currencyListing) {
        this.yahooBackFiller = yahooBackFiller;
        this.currencyListing = currencyListing;
    }

    @RequestMapping(value = currencyRootPath + "/back-fill",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<Object> backFill() {
        BackfillResponse response = new BackfillResponse();
        Map<String, BackfillResponse.CurrencyBackfillInfo> currencies = new HashMap<>();

        LocalDateTime localDate = LocalDateTime.now(UTC);
        response.setCurrentDatetime(localDate);
        LocalDateTime cutDate = localDate.minusMonths(6);
        List<Currency> currencyList = currencyListing.getCurrencyList();
        logger.info("Starting backfill for \"" + currencyList.size() + "\" currencies");

        for (Currency currency : currencyList) {
            USDExchangeRate lastExchangeRecord = yahooBackFiller.getNewestRecord(currency);
            logger.info("Fetching \"" + currency.getCode() + "\" exchange rate");
            if (lastExchangeRecord.getCurrency() != null) {
                cutDate = lastExchangeRecord.getTimestamp();
            }
            CurrencyRefill currencyRefill = yahooBackFiller.runBackfill(localDate, cutDate, currency);

            BackfillResponse.CurrencyBackfillInfo info = new BackfillResponse.CurrencyBackfillInfo(
                    lastExchangeRecord.getTimestamp(),
                    currencyRefill.storedRecords());
            currencies.put(currency.getCode(), info);
        }
        response.setCurrencies(currencies);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
