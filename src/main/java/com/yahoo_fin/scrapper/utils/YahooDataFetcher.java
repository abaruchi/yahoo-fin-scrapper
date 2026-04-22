package com.yahoo_fin.scrapper.utils;

import com.yahoo_fin.scrapper.types.PriceResponse;
import com.yahoo_fin.scrapper.types.yahoo.ChartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="yahoo-finance",
        url = "${app.rapid-api-base-url}",
        configuration = YahooFeignConfig.class)
public interface YahooDataFetcher {

    @GetMapping("/stock/" + "${app.rapid-api-version}" + "/get-chart?interval=1d&range=1d&includePrePost=false&useYfid=true&includeAdjustedClose=true")
    ChartResponse getStockPriceCurrent(@RequestParam("symbol") String symbol);

    @GetMapping("/stock/" + "${app.rapid-api-version}" + "/get-chart?interval=1d&range=6mo&includePrePost=false&useYfid=true&includeAdjustedClose=true")
    List<PriceResponse> getStockPricePreviousSixMonths(@RequestParam("symbol") String symbol);

    @GetMapping ("/stock/" + "${app.rapid-api-version}" + "/get-chart?interval=1d&includePrePost=false&useYfid=true&includeAdjustedClose=true")
    List<PriceResponse> getStockPriceFromToDate(@RequestParam("symbol") String symbol, @RequestParam("period1") String startDate, @RequestParam("period2") String endDate);
}