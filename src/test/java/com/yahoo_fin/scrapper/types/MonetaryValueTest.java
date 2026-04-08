package com.yahoo_fin.scrapper.types;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonetaryValueTest {

    @Test
    void getNormalisedValue() {
        MonetaryValue value = new MonetaryValue(1.20);
        assertEquals(120, value.getNormalisedValue());

        MonetaryValue value2 = new MonetaryValue(1.5870);
        assertEquals(158, value2.getNormalisedValue());

        MonetaryValue value3 = new MonetaryValue(0.5870);
        assertEquals(58, value3.getNormalisedValue());
    }

    @Test
    void getValueFromStringConstructor() {
        MonetaryValue value = new MonetaryValue("1.20");
        assertEquals(1.20, value.getValue());

        value = new MonetaryValue("1.5870");
        assertEquals(1.5870, value.getValue());

        value = new MonetaryValue("0.5870");
        assertEquals(0.5870, value.getValue());

    }

    @Test
    void add() {
        MonetaryValue value1 = new MonetaryValue(1.20);
        MonetaryValue value2 = new MonetaryValue(1.40);
        MonetaryValue result = value1.add(value2);
        assertEquals(260, result.getNormalisedValue());
        assertEquals("2.6", result.toString());
        assertEquals(2.6, result.getValue());

        value1 = new MonetaryValue(1.80);
        value2 = new MonetaryValue(1.40);
        result = value1.add(value2);
        assertEquals(320, result.getNormalisedValue());
        assertEquals("3.2", result.toString());
        assertEquals(3.2, result.getValue());
    }

    @Test
    void multiply() {
        MonetaryValue value1 = new MonetaryValue(1.20);
        MonetaryValue value2 = new MonetaryValue(1.40);
        MonetaryValue result = value1.multiply(value2);
        assertEquals(168, result.getNormalisedValue());
        assertEquals("1.68", result.toString());
        assertEquals(1.68, result.getValue());

        result = value1.multiply(0.5);
        assertEquals(60, result.getNormalisedValue());
        assertEquals("0.6", result.toString());
        assertEquals(0.6, result.getValue());
    }
}