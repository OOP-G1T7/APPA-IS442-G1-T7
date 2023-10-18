package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.PathVariable;

public interface StockService {

    ArrayList<StockEntity> retrieveAllStocks(int portfolioId);

    StockEntity addStockToPortfolio(StockRequestDTO stockDTO, int portfolioId);

    StockEntity editStock(StockRequestDTO stockDTO, @PathVariable int portfolioId);
}
