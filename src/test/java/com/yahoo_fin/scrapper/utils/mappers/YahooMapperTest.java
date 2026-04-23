package com.yahoo_fin.scrapper.utils.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yahoo_fin.scrapper.types.PriceResponse;
import com.yahoo_fin.scrapper.types.yahoo.ChartResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class YahooMapperTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final YahooMapper yahooMapper = new YahooMapper();

    @Test
    void testJsonMapperSingleDayResponse() throws Exception {
        String yahooResponse = """
                            {
                              "chart": {
                                "result": [
                                  {
                                    "meta": {
                                      "longName": "Apple Inc.",
                                      "shortName": "AAPL",
                                      "instrumentType": "EQUITY"
                                    },
                                    "timestamp": [1710000000],
                                    "indicators": {
                                      "quote": [
                                        {
                                          "close": [180.5]
                                        }
                                      ]
                                    }
                                  }
                                ]
                              }
                            }
                """;
        ChartResponse chartResponse = objectMapper.readValue(yahooResponse, ChartResponse.class);
        assertEquals("Apple Inc.", chartResponse.getChart().getResult().getFirst().getMeta().getLongName());
        assertEquals("AAPL", chartResponse.getChart().getResult().getFirst().getMeta().getShortName());
        assertEquals("EQUITY", chartResponse.getChart().getResult().getFirst().getMeta().getInstrumentType());

        assertEquals(1, chartResponse.getChart().getResult().getFirst().getTimestamp().size());
        assertEquals(180.5, chartResponse.getChart().getResult().getFirst().getIndicators().getQuote().getFirst().getClose().getFirst(), 0.001);
    }

    @Test
    void testJsonMapperMultipleDayResponse() throws Exception {
        String yahooResponse = """
                                    {
                              "chart": {
                                "result": [
                                  {
                                    "meta": {
                                      "longName": "BitCoin",
                                      "shortName": "BTC-US",
                                      "instrumentType": "CRYPTOCURRENCY"
                                    },
                                    "timestamp": [1761001200,1761087600,1761174000],
                                    "indicators": {
                                      "quote": [
                                        {
                                          "close": [180.5,181.10,181.20]
                                        }
                                      ]
                                    }
                                  }
                                ]
                              }
                            }
       """;
        ChartResponse chartResponse = objectMapper.readValue(yahooResponse, ChartResponse.class);
        assertEquals("BitCoin", chartResponse.getChart().getResult().getFirst().getMeta().getLongName());
        assertEquals(3, chartResponse.getChart().getResult().getFirst().getTimestamp().size());
        assertEquals(181.20, chartResponse.getChart().getResult().getFirst().getIndicators().getQuote().getFirst().getClose().get(2));
    }

    @Test
    void testMapperWithSingleDayResponse() throws JsonProcessingException {
        String yahooResponse = """
                            {
                              "chart": {
                                "result": [
                                  {
                                    "meta": {
                                      "longName": "Apple Inc.",
                                      "shortName": "AAPL",
                                      "instrumentType": "EQUITY"
                                    },
                                    "timestamp": [1710000000],
                                    "indicators": {
                                      "quote": [
                                        {
                                          "close": [180.5]
                                        }
                                      ]
                                    }
                                  }
                                ]
                              }
                            }
                """;
        ChartResponse chartResponse = objectMapper.readValue(yahooResponse, ChartResponse.class);
        List<PriceResponse> priceResponse = yahooMapper.toPriceResponse(chartResponse);

        assertEquals(1, priceResponse.size());
        assertEquals("AAPL", priceResponse.getFirst().getAssetCode());
        assertEquals("EQUITY", priceResponse.getFirst().getAssetName());
        assertEquals("2024-03-09T16:00", priceResponse.getFirst().getTimestamp().toString());
        assertEquals(180.5, priceResponse.getFirst().getPrice().getRawValue());
    }

    @Test
    void testMapperWithMultipleDayResponse() throws JsonProcessingException {
        String yahooResponse = """
                                    {
                              "chart": {
                                "result": [
                                  {
                                    "meta": {
                                      "longName": "Bitcoin USD",
                                      "shortName": "Bitcoin USD",
                                      "symbol": "BTC-US",
                                      "instrumentType": "CRYPTOCURRENCY"
                                    },
                                    "timestamp": [1761001200,1761087600,1761174000],
                                    "indicators": {
                                      "quote": [
                                        {
                                          "close": [180.5,181.10,181.20]
                                        }
                                      ]
                                    }
                                  }
                                ]
                              }
                            }
                """;
        ChartResponse chartResponse = objectMapper.readValue(yahooResponse, ChartResponse.class);
        List<PriceResponse> priceResponse = yahooMapper.toPriceResponse(chartResponse);
        assertEquals(3, priceResponse.size());
        assertEquals("BTC", priceResponse.get(2).getAssetCode());
        assertEquals("CRYPTOCURRENCY", priceResponse.get(2).getAssetName());
        assertEquals("2025-10-22T23:00", priceResponse.get(2).getTimestamp().toString());
        assertEquals(181.20, priceResponse.get(2).getPrice().getRawValue());
    }

}