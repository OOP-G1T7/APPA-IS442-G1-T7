package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

public interface PortfolioService {

    ArrayList<PortfolioEntity> retrieveAllPortfolios();

    ArrayList<PortfolioEntity> retrieveAllPortfoliosByUserId(int userId);

    PortfolioEntity retrievePortfolio(int portfolioId);

    ResponseEntity<String> createPortfolio(PortfolioEntity newPortfolioEntity);

    ResponseEntity<String> deletePortfolio(int portfolioId);

    ResponseEntity<String> editPortfolio(PortfolioEntity updatedPortfolio);
}
