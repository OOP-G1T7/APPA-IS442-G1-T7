package com.example.analyticsapp.stockwrapper;

import com.example.analyticsapp.stockwrapper.util.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stockwrapper")
public class StockWrapperController {

    @GetMapping("/{stockTicker}")
    public ResponseEntity<?> getStock(@PathVariable String stockTicker) {
        String apiKey = "4U3NNSG5OHR1CBIG";

        try {
            StockWrapperService stockService = new StockWrapperService(apiKey);
            List<Map<String, String>> result = stockService.getDailyStockData(stockTicker);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result);
        } catch (TickerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/stockSearch/{search}")
    public ResponseEntity<?> searchStocks(@PathVariable String search) {
        String apiKey = "4U3NNSG5OHR1CBIG";

        try {
            StockWrapperService stockService = new StockWrapperService(apiKey);
            List<Map<String, String>> result = stockService.searchStocks(search);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
