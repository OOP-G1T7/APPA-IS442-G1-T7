package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.analyticsapp.portfolio.PortfolioEntity;
import com.example.analyticsapp.portfolio.PortfolioRepository;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private StockService stockService;

    @GetMapping("")
    public ArrayList<PortfolioEntity> retrieveAllPortfolios() {
        
        ArrayList<PortfolioEntity> result = portfolioService.retrieveAllPortfolios();
        return result;
    }

    @GetMapping("/user/{userId}")
    public ArrayList<PortfolioEntity> retrieveAllPortfoliosByUserId(@PathVariable int userId) {
        return portfolioService.retrieveAllPortfoliosByUserId(userId);
    }

    @GetMapping("/pid/{portfolioId}")
    public PortfolioEntity retrievePortfolio(@PathVariable int portfolioId) {
        return portfolioService.retrievePortfolio(portfolioId);
    }

    @GetMapping("/{portfolioId}/{userId}/{name}/{description}")
    public void editPortfolio(@PathVariable int portfolioId, @PathVariable int userId, @PathVariable String name, @PathVariable String description) {
        portfolioService.editPortfolio(portfolioId, userId, name, description);
    }

    @GetMapping("/{portfolioId}/stocks")
    public ArrayList<StockEntity> retrieveAllStocks(@PathVariable int portfolioId) {
        return stockService.retrieveAllStocks(portfolioId);
    }

    @PostMapping
    public PortfolioEntity createPortfolio(@RequestBody int userId, String name, String description) {
        return portfolioService.createPortfolio(userId, name, description);
    }
}
