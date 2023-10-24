package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

public class StockPortfolio {
    private int portfolioId;
    private ArrayList<String> stockTickers = new ArrayList<>();

    public StockPortfolio(int portfolioId, ArrayList<String> stockTickers) {
        this.portfolioId = portfolioId;
        this.stockTickers = stockTickers;
    }

    public int getPortfolioId() {
        return this.portfolioId;
    }

    public ArrayList<String> getPortfolioStockTickers() {
        return this.stockTickers;
    }
}
