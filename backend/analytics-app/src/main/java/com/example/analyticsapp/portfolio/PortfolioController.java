package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.analyticsapp.portfolio.PortfolioEntity;
import com.example.analyticsapp.portfolio.PortfolioRepository;

@RestController
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
    public PortfolioEntity createPortfolio(@RequestBody PortfolioEntity newPortfolioEntity) {
        System.out.println("createPortfolio and hello world");
        return portfolioService.createPortfolio(newPortfolioEntity);
    }

    @PostMapping("/portfolio/{portfolioId}")
    public ResponseEntity<StockEntity> addStockToPortfolio(@RequestBody StockRequestDTO stockDTO,
            @PathVariable int portfolioId) {
        StockEntity stock = stockService.addStockToPortfolio(stockDTO, portfolioId);
        return ResponseEntity.ok(stock);
    }

    @PutMapping("/portfolio")
    public ResponseEntity<PortfolioEntity> editPortfolio(@RequestBody PortfolioEntity updatedPortfolio) {
        PortfolioEntity portfolio = portfolioService.editPortfolio(updatedPortfolio);
        return ResponseEntity.ok(portfolio);
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
    public ResponseEntity<StockEntity> editStock(@RequestBody StockRequestDTO stockDTO, @PathVariable int portfolioId) {
        StockEntity stock = stockService.editStock(stockDTO, portfolioId);
        return ResponseEntity.ok(stock);
    }

}
