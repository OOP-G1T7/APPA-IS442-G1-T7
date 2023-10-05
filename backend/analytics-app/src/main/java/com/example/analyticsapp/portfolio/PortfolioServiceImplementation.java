package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Qualifier("PortfolioServiceImpl")
@Service
public class PortfolioServiceImplementation implements PortfolioService{

    @Autowired
    PortfolioRepository portfolioRepository;

    public ArrayList<PortfolioEntity> getAll() {
        ArrayList<PortfolioEntity> result = portfolioRepository.getAll();
        return result;
    }


    public ArrayList<PortfolioEntity> getAllPortfolios(int userId) {
        ArrayList<PortfolioEntity> result = portfolioRepository.getAllPortfolios(userId);
        return result;
    }

    public PortfolioEntity getOnePortfolio(long portfolioId) {
        PortfolioEntity result = portfolioRepository.getOnePortfolio(portfolioId);
        return result;
    }
}