package com.example.analyticsapp.stock;

import jakarta.persistence.*;
import com.example.analyticsapp.portfolio.PortfolioEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "portfolio_stock")
@IdClass(StockEntityId.class)
public class StockEntity {

    @Id
    @Column(name = "ticker")
    private String ticker;

    @Id
    @Column(name = "portfolio_id")
    private int portfolioId;

    @Column(name = "quantity")
    private int quantity; 

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private PortfolioEntity portfolio;

    public int getQuantity() {
        return quantity;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public String getTicker() {
        return ticker;
    }

    public PortfolioEntity getPortfolio() {
        return portfolio;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setPortfolio(PortfolioEntity portfolio) {
        this.portfolio = portfolio;
    }



    

    // Constructors, getters, setters, and other methods
}
