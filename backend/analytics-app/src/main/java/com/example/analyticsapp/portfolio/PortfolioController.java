package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    @Qualifier("PortfolioServiceImpl")
    private PortfolioService portfolioService;

    @GetMapping("")
    public String portfolioHello() {
        return "Portfolio Check!";
    }

    @GetMapping("/get-all")
    public ArrayList<PortfolioEntity> getAll() {
        
        ArrayList<PortfolioEntity> result = portfolioService.getAll();
        return result;
    }

    /**
     * Retrieve a list of all portfolios based on userid.
     * @param userId
     * @return A list of PortfolioEntity objects.
     */
    @GetMapping("/get-all/{userId}")
    public ArrayList<PortfolioEntity> getAllPortfolios(@PathVariable int userId) {
        
        ArrayList<PortfolioEntity> result = portfolioService.getAllPortfolios(userId);
        return result;
    }

    

    /**
     * Retrieve a one portfolio based on the portfolioid.
     *
     * @return A PortfolioEntity object.
     */
    @GetMapping("/get-one/{portfolioId}")
    public PortfolioEntity getOnePortfolio(@PathVariable long portfolioId) {
        PortfolioEntity result = portfolioService.getOnePortfolio(portfolioId);
        return result;
    }


}
