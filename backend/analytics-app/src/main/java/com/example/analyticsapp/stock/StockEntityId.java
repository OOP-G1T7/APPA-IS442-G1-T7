package com.example.analyticsapp.stock;

// import jakarta.persistence.*;
import java.io.*;

public class StockEntityId implements Serializable {
    private long portfolioId;
    private String ticker;

    public StockEntityId(long portfolioId, String ticker) {
        this.portfolioId = portfolioId;
        this.ticker = ticker;
    }

    public long getPortfolioId() {
        return portfolioId;
    }

    public String getTicker() {
        return ticker;
    }


    // researching on the need for an equals and hashcode method
    // website: https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
}
