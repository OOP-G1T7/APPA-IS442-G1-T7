package com.example.analyticsapp.portfolio;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="portfolio_stock")
public class StockEntity {
    
    @EmbeddedId
    private StockPK stockPk;

    @Column(name = "proportion")
    private double proportion;

    @OneToOne
    @MapsId("portfolioId")
    @JoinColumn(name = "portfolio_id", referencedColumnName = "portfolio_id")
    private PortfolioEntity portfolioEntity;

    public StockPK getStockPk() {
        return stockPk;
    }

    public void setStockPk(StockPK stockPk) {
        this.stockPk = stockPk;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

    @Override
    public String toString() {
        return "StockEntity [stockPk=" + stockPk + ", proportion=" + proportion + "]";
    }
    public void setPortfolio(PortfolioEntity portfolioEntity) {
        this.portfolioEntity = portfolioEntity;
    }
    
}
