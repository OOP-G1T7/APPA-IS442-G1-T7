package com.example.analyticsapp.portfolio;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "portfolio")
public class PortfolioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private int portfolioId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "capital")
    private double capital;

    @OneToMany(mappedBy = "portfolioEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    // need to use List not ArrayList
    private List<StockEntity> stocks = new ArrayList<>();


    // Getters and Setters

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public List<StockEntity> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockEntity> stocks) {
        this.stocks = stocks;
    }

    public void addStock(StockEntity stock) {
        stocks.add(stock);
        stock.setPortfolio(this);
    }


    public String toString() {
        return "PortfolioEntity [portfolioId=" + portfolioId + ", userId=" + userId + ", name=" + name
                + ", description="
                + description + ", stocks=" + stocks + "]";
    }

}
