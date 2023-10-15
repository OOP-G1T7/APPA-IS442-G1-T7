package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImplementation implements StockService{
    
    @Autowired
    private StockRepository stockRepo;

    @Override
    public StockEntity addStock(int portfolio_id, String ticker, int quantity) {
        StockEntity stock = new StockEntity();
        StockPK stockPk = new StockPK();
        stockPk.setPortfolioId(portfolio_id);
        stockPk.setTicker(ticker);
        stock.setStockPk(stockPk);
        stock.setQuantity(quantity);
        return stockRepo.save(stock);
    }

    @Override
    public ArrayList<StockEntity> retrieveAllStocks(int portfolio_id) {
        ArrayList<StockEntity> retrievedStocks = stockRepo.getAllStocks(portfolio_id);
        return retrievedStocks;
    }

    @Override
    public void editStock(int portfolio_id, String ticker, int quantity) {
        StockEntity retrievedStock = stockRepo.getOneStock(portfolio_id, ticker);
        retrievedStock.setQuantity(quantity);
        stockRepo.save(retrievedStock);
    }
}
