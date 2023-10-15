package com.example.analyticsapp.portfolio;

public interface PortfolioService {


    PortfolioEntity createPortfolio(int userId, String name, String description);


    PortfolioEntity retrievePortfolio(int portfolioId);

    void editPortfolio(int portfolioId, int userId, String name, String description);
}
