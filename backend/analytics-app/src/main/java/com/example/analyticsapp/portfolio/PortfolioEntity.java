package com.example.analyticsapp.portfolio;

import jakarta.persistence.*;

import java.util.*;

import com.example.analyticsapp.stock.StockEntity;

@Entity
public class PortfolioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long portfolioId;

    private int userId;
    private String name;
    private String description;

    // Need one to many mapping to StockEntity
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockEntity> stocks = new ArrayList<>();

    public long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public int getUserId() {
        return userId;
    }

    // Should there be a setter for UserId?
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

    public List<StockEntity> getStocks() {
        return stocks;
    }



}
