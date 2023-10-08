package com.example.analyticsapp.stock;

import jakarta.persistence.*;
import com.example.analyticsapp.portfolio.PortfolioEntity;

@Entity
@IdClass(StockEntityId.class)
@Table(name = "ticker")
public class StockEntity {

    @Id
    private String ticker;
    @Id
    private long portfolioId;
    
    @MapsId("portfolioId")
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private PortfolioEntity portfolio;

    // Constructors, getters, setters, and other methods
}
