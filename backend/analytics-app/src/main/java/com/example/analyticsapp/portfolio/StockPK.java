package com.example.analyticsapp.portfolio;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class StockPK implements Serializable{
    
    @Column(name = "portfolio_id",insertable = false, updatable = false)
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

    public String toString() {
        return "StockPK [portfolioId=" + portfolioId + ", ticker=" + ticker + "]";
    }
    
}
