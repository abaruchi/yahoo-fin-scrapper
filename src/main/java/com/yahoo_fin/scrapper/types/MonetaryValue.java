package com.yahoo_fin.scrapper.types;

import java.util.Locale;

public class MonetaryValue {
    private final int normalisedValue;
    private final double rawValue;
    private final Locale currLocale = java.util.Locale.US;
    private final int powerOfTen = 2;

    public MonetaryValue(double rawValue) {
        this.rawValue = rawValue;
        this.normalisedValue = (int) (rawValue * Math.pow(10, powerOfTen));
    }

    public MonetaryValue(String sValue) throws IllegalArgumentException {
        try {
            this.rawValue = Double.parseDouble(sValue);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid monetary value format: " + sValue, e);
        }
        this.normalisedValue = (int) (this.rawValue * Math.pow(10, powerOfTen));
    }

    public double getRawValue() {
        return rawValue;
    }

    public int getNormalisedValue() {
        return normalisedValue;
    }

    public String toString() {
        double valueTruncated = (normalisedValue / Math.pow(10, powerOfTen));
        return String.format(currLocale, "%.2f", valueTruncated);
    }

    public MonetaryValue add(MonetaryValue other) {
        int result = this.normalisedValue + other.normalisedValue;
        return new MonetaryValue((double)result/ Math.pow(10, powerOfTen));
    }

    public MonetaryValue add(double factor) {
        double result = this.normalisedValue + factor;
        return new MonetaryValue(result);
    }

    public MonetaryValue multiply(double factor) {
        MonetaryValue other = new MonetaryValue(factor);
        return this.multiply(other);
    }

    public MonetaryValue multiply(MonetaryValue other) {
        int interResult = this.normalisedValue * other.normalisedValue;
        double decimalResult = (interResult / Math.pow(10, powerOfTen*2));
        return new MonetaryValue(decimalResult);
    }

    public MonetaryValue divide(double factor) {
        MonetaryValue other = new MonetaryValue(factor);
        return this.divide(other);
    }

    public MonetaryValue divide(MonetaryValue other) {
        double result = this.rawValue / other.rawValue;
        return new MonetaryValue(result);
    }
}
