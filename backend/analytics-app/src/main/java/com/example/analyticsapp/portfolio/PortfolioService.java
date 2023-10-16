package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import com.example.analyticsapp.stock.StockEntityDTO;

public interface PortfolioService {

    ArrayList<PortfolioEntity> getAll();

    ArrayList<PortfolioEntity> getAllPortfolios(int userid);

    PortfolioEntity getOnePortfolio(int portfolioId);

    // PortfolioWithStocksDTO getPortfolioWithStocks(int portfolioId);


    // PortfolioWithStocksDTO createPortfolioDTO(PortfolioEntity portfolio);

    PortfolioEntity addStockToPortfolio(StockEntityDTO stockDTO);

    

    
    // Yet to complete

    // void createPortfolio(); 
    // void editPortfolio(); 

    




    
}
