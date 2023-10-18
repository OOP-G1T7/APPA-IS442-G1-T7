package com.example.analyticsapp.portfolio;

public class StockRequestDTO {
    // private int portfolioId;
    private String ticker;
    private int quantity;


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
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
