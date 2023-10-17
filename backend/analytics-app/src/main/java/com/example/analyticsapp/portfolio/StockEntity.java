package com.example.analyticsapp.portfolio;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="portfolio_stock")
public class StockEntity {
    
    @EmbeddedId
    private StockPK stockPk;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", referencedColumnName = "portfolio_id")
    private PortfolioEntity portfolioEntity;

    public StockPK getStockPk() {
        return stockPk;
    }

    public void setStockPk(StockPK stockPk) {
        this.stockPk = stockPk;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "StockEntity [stockPk=" + stockPk + ", quantity=" + quantity + "]";
    }
    // public void setPortfolio(PortfolioEntity portfolioEntity) {
    //     this.portfolioEntity = portfolioEntity;
    // }
    
}
