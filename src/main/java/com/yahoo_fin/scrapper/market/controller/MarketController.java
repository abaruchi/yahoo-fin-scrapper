package com.yahoo_fin.scrapper.market.controller;

import com.yahoo_fin.scrapper.market.service.DBPreFillerService;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.logging.Logger;

@RestController
public class MarketController {

    private static final Logger logger = Logger.getLogger(MarketController.class.getName());

    private final String marketRootPath = "/market";
    private final DBPreFillerService dbPreFillerService;

    public MarketController(DBPreFillerService dbPreFillerService) {
        this.dbPreFillerService = dbPreFillerService;
    }

    @RequestMapping(value = marketRootPath + "/prepare-db",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<Object> prepareDB() {
        @Data
        class prepareDBResponse {
            String message;
        }

        logger.info("Preparing database");
        dbPreFillerService.populateCountryAndMarketTables();
        prepareDBResponse response = new prepareDBResponse();
        response.message = "Database prepared";
        return ResponseEntity.ok(response);
    }
}
