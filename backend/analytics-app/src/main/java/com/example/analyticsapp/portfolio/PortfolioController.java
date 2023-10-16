package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.analyticsapp.stock.StockEntityDTO;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    @Qualifier("PortfolioServiceImpl")
    private PortfolioService portfolioService;

    @GetMapping("/test")
    public String portfolioHello() {
        return "Portfolio Check!";
    }

    @GetMapping("")
    public ArrayList<PortfolioEntity> getAll() {
        
        ArrayList<PortfolioEntity> result = portfolioService.getAll();
        return result;
    }

    /**
     * Retrieve a list of all portfolios based on userid.
     * @param userId
     * @return A list of PortfolioEntity objects.
     */
    @GetMapping("/{userId}")
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
    public PortfolioEntity getOnePortfolio(@PathVariable int portfolioId) {
        PortfolioEntity result = portfolioService.getOnePortfolio(portfolioId);
        return result;
    }

    @PostMapping
    public PortfolioEntity addStockToPortfolio(@RequestBody StockEntityDTO stockDTO) {
        PortfolioEntity result = portfolioService.addStockToPortfolio(stockDTO);
        return result;
    }


}
