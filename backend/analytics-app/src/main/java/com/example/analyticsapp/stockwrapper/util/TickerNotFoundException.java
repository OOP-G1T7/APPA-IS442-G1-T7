package com.example.analyticsapp.stockwrapper.util;

public class TickerNotFoundException extends RuntimeException {
    public TickerNotFoundException(String stockTicker) {
        super("No daily data found for stock: " + stockTicker);
    }
}
