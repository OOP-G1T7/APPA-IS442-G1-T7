package com.example.analyticsapp.portfolio;

import java.util.ArrayList;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class StockServiceImplementation implements StockService {

    @Autowired
    private StockRepository stockRepo;

    @Autowired
    private PortfolioRepository portfolioRepo;

    @Override
    public ResponseEntity<String> addStockToPortfolio(ArrayList<StockRequestDTO> stockDTO, int portfolioId) {
        JSONObject response = new JSONObject();
        PortfolioEntity portfolio = portfolioRepo.getPortfolio(portfolioId);
        if (portfolio == null) {
            response.put("message", "Portfolio not found");
            return new ResponseEntity<>(response.toString(), HttpStatus.NOT_FOUND);
        }

        for (StockRequestDTO stock : stockDTO) {
            StockEntity stockEntity = new StockEntity();

            StockPK stockPk = new StockPK();
            stockPk.setPortfolioId(portfolioId);
            stockPk.setTicker(stock.getTicker());
            stockEntity.setStockPk(stockPk);
            stockEntity.setProportion(stock.getProportion());

            portfolio.addStock(stockEntity);
            stockRepo.save(stockEntity);
        }
            response.put("message", "Stocks added successfully");
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
            
    }

    @Override
    public ResponseEntity<String> deleteStocksFromPortfolio(int portfolioId, ArrayList<String> stockTickers) {
        Integer count = stockTickers.size();
        JSONObject response = new JSONObject();
        for (String stockTicker : stockTickers) {
            StockEntity stock = stockRepo.getOneStock(portfolioId, stockTicker);
            System.out.print(stockTicker);
            if (stock instanceof StockEntity) {
                stockRepo.delete(stock);
                count--;
            }
        }

        if (count == 0) {
            response.put("message", "Stock(s) deleted successfully from portfolio");
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        }
        response.put("message", "Stocks could not be deleted from portfolio");
        return new ResponseEntity<>(response.toString(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ArrayList<StockEntity> retrieveAllStocks(int portfolioId) {
        ArrayList<StockEntity> retrievedStocks = stockRepo.getAllStocks(portfolioId);
        return retrievedStocks;
    }

    @Override
    public ResponseEntity<String> editStock(ArrayList<StockRequestDTO> stockDTO, @PathVariable int portfolioId) {
        JSONObject response = new JSONObject();

        try {
            for (StockRequestDTO stock : stockDTO) {
                StockEntity stockEntity = stockRepo.getOneStock(portfolioId, stock.getTicker());
                stockEntity.setProportion(stock.getProportion());
                stockRepo.save(stockEntity);
            }

            response.put("message", "Stocks edited successfully");
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);

        } catch (Exception e) {
            response.put("message", "Failed to update stock");
            return new ResponseEntity<>(response.toString(), HttpStatus.BAD_GATEWAY);
        }

    }
}
