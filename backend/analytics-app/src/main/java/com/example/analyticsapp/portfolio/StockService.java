package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

public interface StockService {
    
    StockEntity addStock(int portfolio_id, String ticker, int quantity);

    ArrayList<StockEntity> retrieveAllStocks(int portfolio_id);

    void editStock(int portfolio_id, String ticker, int quantity);
}
