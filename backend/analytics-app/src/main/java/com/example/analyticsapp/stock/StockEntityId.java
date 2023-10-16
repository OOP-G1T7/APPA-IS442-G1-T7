package com.example.analyticsapp.stock;

// import jakarta.persistence.*;
import java.io.*;
import java.util.Objects;

public class StockEntityId implements Serializable {
    private int portfolioId;
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


    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockEntityId stockId = (StockEntityId) o;
        return Objects.equals(ticker, stockId.ticker) &&
               Objects.equals(portfolioId, stockId.portfolioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker, portfolioId);
    }


    // researching on the need for an equals and hashcode method
    // website: https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
}
