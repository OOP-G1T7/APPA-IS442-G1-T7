package com.example.analyticsapp.portfolio;

public class StockRequestDTO {
    // private int portfolioId;
    private String ticker;
    private double proportion;


    // public int getPortfolioId() {
    //     return portfolioId;
    // }
    // public void setPortfolio_id(int portfolioId) {
    //     this.portfolioId = portfolioId;
    // }
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
