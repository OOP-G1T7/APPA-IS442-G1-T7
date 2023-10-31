package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins =  "*" )
@RequestMapping("/api")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private StockService stockService;

    @GetMapping("/portfolios")
    public ArrayList<PortfolioEntity> retrieveAllPortfolios() {

        ArrayList<PortfolioEntity> result = portfolioService.retrieveAllPortfolios();
        return result;
    }

    @GetMapping("/portfolios/{userId}")
    public ArrayList<PortfolioEntity> retrieveAllPortfoliosByUserId(@PathVariable int userId) {
        return portfolioService.retrieveAllPortfoliosByUserId(userId);
    }

    @GetMapping("/portfolio/{portfolioId}")
    public PortfolioEntity retrievePortfolio(@PathVariable int portfolioId) {
        return portfolioService.retrievePortfolio(portfolioId);
    }

    @GetMapping("/portfolio/{portfolioId}/stocks")
    public ArrayList<StockEntity> retrieveAllStocks(@PathVariable int portfolioId) {
        return stockService.retrieveAllStocks(portfolioId);
    }

    @PostMapping("/portfolio")
    public ResponseEntity<String> createPortfolio(@RequestBody PortfolioEntity newPortfolioEntity) {
        return portfolioService.createPortfolio(newPortfolioEntity);
    }

    @PostMapping("/portfolio/{portfolioId}")
    public ResponseEntity<String> addStockToPortfolio(@RequestBody ArrayList<StockRequestDTO> stockDTO,
            @PathVariable int portfolioId) {
        return stockService.addStockToPortfolio(stockDTO, portfolioId);
    }

    @PutMapping("/portfolio")
    public ResponseEntity<String> editPortfolio(@RequestBody PortfolioEntity updatedPortfolio) {
        return portfolioService.editPortfolio(updatedPortfolio);
        
    }

    @DeleteMapping("/portfolio/{portfolioId}")
    public ResponseEntity<String> deletePortfolio(@PathVariable int portfolioId) {
        return portfolioService.deletePortfolio(portfolioId);
    }

    @DeleteMapping("/portfolio/stocks")
    public ResponseEntity<String> deleteStocksFromPortfolio(@RequestBody StockPortfolio portfolioStocks) {
        int portfolioId = portfolioStocks.getPortfolioId();
        ArrayList<String> stockTickers = portfolioStocks.getPortfolioStockTickers();
        return stockService.deleteStocksFromPortfolio(portfolioId, stockTickers);
    }

    @PutMapping("/portfolio/stock/{portfolioId}")
    public ResponseEntity<String> editStock(@RequestBody ArrayList<StockRequestDTO> stockDTO, @PathVariable int portfolioId) {
        return stockService.editStock(stockDTO, portfolioId);
    }

}
