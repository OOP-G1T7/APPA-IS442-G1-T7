package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface StockService {

    ArrayList<StockEntity> retrieveAllStocks(int portfolioId);

    ResponseEntity<String> addStockToPortfolio(StockRequestDTO stockDTO, int portfolioId);

    ResponseEntity<String> deleteStocksFromPortfolio(int portfolioId, ArrayList<String> stockTickers);

    ResponseEntity<String> editStock(StockRequestDTO stockDTO, @PathVariable int portfolioId);
}
