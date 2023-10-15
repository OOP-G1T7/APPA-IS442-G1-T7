package com.example.analyticsapp.portfolio;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class StockPK {
    
    @Column(name = "portfolio_id")
    private int portfolioId;

    @Column(name = "ticker")
    private String ticker;

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    
}
