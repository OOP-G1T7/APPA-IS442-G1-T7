package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

public interface PortfolioService {

    ArrayList<PortfolioEntity> getAll();

    /* */
    ArrayList<PortfolioEntity> getAllPortfolios(int userid);

    PortfolioEntity getOnePortfolio(long portfolioId);

    
    // Yet to complete

    // void createPortfolio(); 
    // void editPortfolio(); 

    




    
}
