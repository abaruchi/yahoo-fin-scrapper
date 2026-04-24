package com.yahoo_fin.scrapper.utils.mappers.yahoo;

import com.yahoo_fin.scrapper.types.yahoo.ChartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="yahoo-finance",
        url = "https://yh-finance.p.rapidapi.com",
        configuration = YahooFeignConfig.class)
public interface YahooDataFetcher {

    @GetMapping("/stock/" + "v3" + "/get-chart?interval=1d&range=1d&includePrePost=false&useYfid=true&includeAdjustedClose=true")
    ChartResponse getStockPriceCurrent(@RequestParam("symbol") String symbol);

    @GetMapping("/stock/" + "v3" + "/get-chart?interval=1d&range=6mo&includePrePost=false&useYfid=true&includeAdjustedClose=true")
    ChartResponse getStockPricePreviousSixMonths(@RequestParam("symbol") String symbol);

    @GetMapping ("/stock/" + "v3" + "/get-chart?interval=1d&includePrePost=false&useYfid=true&includeAdjustedClose=true")
    ChartResponse getStockPriceFromToDate(@RequestParam("symbol") String symbol, @RequestParam("period1") String startDate, @RequestParam("period2") String endDate);
}
