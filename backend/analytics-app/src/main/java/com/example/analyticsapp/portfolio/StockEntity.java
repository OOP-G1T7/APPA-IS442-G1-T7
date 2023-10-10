package com.example.analyticsapp.portfolio;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="portfolio_stock")
public class StockEntity {
    
    @Id
    private String ticker;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", referencedColumnName = "portfolio_id")
    private int portfolioId;

    private int quantity;

    public String getTicker() {
        return ticker;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
}
