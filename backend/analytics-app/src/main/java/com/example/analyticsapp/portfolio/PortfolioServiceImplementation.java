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
    public PortfolioEntity createPortfolio(PortfolioEntity newPortfolioEntity) {
        return portfolioRepo.save(newPortfolioEntity);
    }

    @Override
    public PortfolioEntity editPortfolio(PortfolioEntity updatedPortfolio) {
        return portfolioRepo.save(updatedPortfolio);
    }
}
