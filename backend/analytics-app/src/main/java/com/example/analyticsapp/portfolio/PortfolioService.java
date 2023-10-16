package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

public interface PortfolioService {

    ArrayList<PortfolioEntity> retrieveAllPortfolios();

    ArrayList<PortfolioEntity> retrieveAllPortfoliosByUserId(int userId);

    PortfolioEntity retrievePortfolio(int portfolioId);

    void editPortfolio(int portfolioId, int userId, String name, String description);

    PortfolioEntity createPortfolio(int userId, String name, String description);
}
