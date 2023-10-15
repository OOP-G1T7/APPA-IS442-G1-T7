package com.example.analyticsapp.portfolio;

import java.lang.reflect.Array;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="portfolio")
public class PortfolioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int portfolioId;
    private int userId;
    private String name;
    private String description;

    @OneToMany(mappedBy = "portfolioEntity")
    private ArrayList<StockEntity> stocks = new ArrayList<StockEntity>();

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    

}
