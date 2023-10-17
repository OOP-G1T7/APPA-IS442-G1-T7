package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

public interface PortfolioService {

    ArrayList<PortfolioEntity> retrieveAllPortfolios();

    ArrayList<PortfolioEntity> retrieveAllPortfoliosByUserId(int userId);

    PortfolioEntity retrievePortfolio(int portfolioId);

    PortfolioEntity createPortfolio(PortfolioEntity newPortfolioEntity);

    PortfolioEntity editPortfolio(PortfolioEntity updatedPortfolio);
}
