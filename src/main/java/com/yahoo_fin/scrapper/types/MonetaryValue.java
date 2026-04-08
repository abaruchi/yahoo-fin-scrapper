package com.yahoo_fin.scrapper.types;

public class MonetaryValue {
    private final int normalised_value;
    private final double value;

    public MonetaryValue(double value) {
        this.value = value;
        this.normalised_value = (int) (value * Math.pow(10, 2));
    }

    public MonetaryValue(String sValue) {
        try {
            this.value = Double.parseDouble(sValue);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid monetary value format: " + sValue, e);
        }
        this.normalised_value = (int) (this.value * Math.pow(10, 2));
    }

    public double getValue() {
        return value;
    }

    public int getNormalisedValue() {
        return normalised_value;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public MonetaryValue add(MonetaryValue other) {
        int result = this.normalised_value + other.normalised_value;
        return new MonetaryValue((double)result/100);
    }

    public MonetaryValue add(double factor) {
        double result = this.normalised_value + factor;
        return new MonetaryValue(result);
    }

    public MonetaryValue multiply(double factor) {
        MonetaryValue other = new MonetaryValue(factor);
        return this.multiply(other);
    }

    public MonetaryValue multiply(MonetaryValue other) {
        int interResult = this.normalised_value * other.normalised_value;
        double decimalResult = (interResult / Math.pow(10, 4));
        return new MonetaryValue(decimalResult);
    }
}
