package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class StockServiceImplementation implements StockService{
    
    @Autowired
    private StockRepository stockRepo;

    @Autowired
    private PortfolioRepository portfolioRepo;

    @Override
    public StockEntity addStockToPortfolio(StockRequestDTO stockDTO, int portfolioId) {
        PortfolioEntity portfolio = portfolioRepo.getPortfolio(portfolioId);
        StockEntity stock = new StockEntity();
        StockPK stockPk = new StockPK();

        stockPk.setPortfolioId(portfolioId);
        stockPk.setTicker(stockDTO.getTicker());

        stock.setStockPk(stockPk);
        stock.setQuantity(stockDTO.getQuantity());
        portfolio.addStock(stock);
        return stockRepo.save(stock);
    }

    @Override
    public ArrayList<StockEntity> retrieveAllStocks(int portfolioId) {
        ArrayList<StockEntity> retrievedStocks = stockRepo.getAllStocks(portfolioId);
        return retrievedStocks;
    }

    @Override
    public StockEntity editStock(StockRequestDTO stockDTO, @PathVariable int portfolioId) {
        StockEntity stock = stockRepo.getOneStock(portfolioId, stockDTO.getTicker());
        stock.setQuantity(stockDTO.getQuantity());
        return stockRepo.save(stock);

        
    }
}
