package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioServiceImplementation implements PortfolioService{

    @Autowired
    private PortfolioRepository portfolioRepo;  
    
    @Override
    public ArrayList<PortfolioEntity> retrieveAllPortfolios() {
        ArrayList<PortfolioEntity> result = portfolioRepo.getAllPortfolios();
        return result;
    }

    public ArrayList<PortfolioEntity> retrieveAllPortfoliosByUserId(int userId) {
        ArrayList<PortfolioEntity> result = portfolioRepo.getAllPortfoliosByUserId(userId);
        return result;
    }

    @Override
    public PortfolioEntity retrievePortfolio(int portfolioId) {
        PortfolioEntity retrieved = portfolioRepo.getPortfolio(portfolioId);
        return retrieved;
    }

    @Override
    public void editPortfolio(int portfolioId, int userId, String name, String description) {
        PortfolioEntity retrieved = portfolioRepo.getPortfolio(portfolioId);
        retrieved.setDescription(description);
        retrieved.setName(name);
        portfolioRepo.save(retrieved);
    }

     @Override
    public PortfolioEntity createPortfolio(PortfolioEntity newPortfolioEntity) {
        return portfolioRepo.save(newPortfolioEntity);
    }

    // @Override
    // public PortfolioEntity addStockToPortfolio(StockEntity stock, int portfolioId) {
    //     PortfolioEntity retrieved = portfolioRepo.getPortfolio(portfolioId);
    //     retrieved.getStocks().add(stock);
    //     return portfolioRepo.save(retrieved);
    // }
}
