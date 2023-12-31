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
import com.example.analyticsapp.log.AuditLogService;

@RestController
@CrossOrigin(origins =  "*" )
@RequestMapping("/api")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private StockService stockService;

    @Autowired
    private AuditLogService auditLogService;

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
        ResponseEntity<String> response = portfolioService.createPortfolio(newPortfolioEntity);
        int userId = newPortfolioEntity.getUserId();
        String detail = "Created portfolio with id = " + newPortfolioEntity.getPortfolioId();
        auditLogService.logAuditEvent(userId, detail, response.getStatusCode());
        return response;
    }

    @PostMapping("/portfolio/{portfolioId}")
    public ResponseEntity<String> addStockToPortfolio(@RequestBody ArrayList<StockRequestDTO> stockDTO, @PathVariable int portfolioId) {
        ResponseEntity<String> response = stockService.addStockToPortfolio(stockDTO, portfolioId);
        String detail = "Added stock comprising of ";
        int userId = portfolioService.retrievePortfolio(portfolioId).getUserId();  
        for (StockRequestDTO stockRequestDTO : stockDTO) {
            detail += stockRequestDTO.getProportion() + "% " + stockRequestDTO.getTicker() + ", ";
            // detail += stockRequestDTO.getProportion() + " " + stockRequestDTO.getTicker() + ", ";
        }
        detail = detail.substring(0, detail.length() - 2);
        detail += " to portfolio with id = " + portfolioId;
        auditLogService.logAuditEvent(userId, detail, response.getStatusCode());
        return response;
        
    }

    @PutMapping("/portfolio")
    public ResponseEntity<String> editPortfolio(@RequestBody PortfolioEntity updatedPortfolio) {
        ResponseEntity<String> response = portfolioService.editPortfolio(updatedPortfolio);
        int userId = updatedPortfolio.getUserId();
        String detail = "Edited portfolio with id = " + updatedPortfolio.getPortfolioId();
        auditLogService.logAuditEvent(userId, detail, response.getStatusCode());
        return response;
    }

    @DeleteMapping("/portfolio/{portfolioId}")
    public ResponseEntity<String> deletePortfolio(@PathVariable int portfolioId) {
        int userId = portfolioService.retrievePortfolio(portfolioId).getUserId();
        ResponseEntity<String> response = portfolioService.deletePortfolio(portfolioId);
        String detail = "Deleted portfolio with id = " + portfolioId;
        auditLogService.logAuditEvent(userId, detail, response.getStatusCode());
        return response;
    }

    @DeleteMapping("/portfolio/stocks")
    public ResponseEntity<String> deleteStocksFromPortfolio(@RequestBody StockPortfolio portfolioStocks) {
        int portfolioId = portfolioStocks.getPortfolioId();
        int userId = portfolioService.retrievePortfolio(portfolioId).getUserId();
        ArrayList<String> stockTickers = portfolioStocks.getPortfolioStockTickers();
        ResponseEntity<String> response = stockService.deleteStocksFromPortfolio(portfolioId, stockTickers);
        String detail = "Deleted stock in portfolio with id = " + portfolioId;
        auditLogService.logAuditEvent(userId, detail, response.getStatusCode());
        return response;
    }

    @PutMapping("/portfolio/stock/{portfolioId}")
    public ResponseEntity<String> editStock(@RequestBody ArrayList<StockRequestDTO> stockDTO, @PathVariable int portfolioId) {
        ResponseEntity<String> response = stockService.editStock(stockDTO, portfolioId);
        int userId = portfolioService.retrievePortfolio(portfolioId).getUserId();
        String detail = "Edited stock in portfolio with id = " + portfolioId;
        auditLogService.logAuditEvent(userId, detail, response.getStatusCode());
        return response;
        
    }

}
