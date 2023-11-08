package com.example.analyticsapp.portfolio;

public class StockRequestDTO {
    private String ticker;
    private double proportion;

    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    public double getProportion() {
        return proportion;
    }
    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

}
