package com.example.analyticsapp.stockwrapper;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/stockwrapper")
public class StockWrapperController {

    @GetMapping("/{stockTicker}")
    public ResponseEntity<String> getStock(@PathVariable String stockTicker) {
        String apiKey = "QJOEZUUAALL4GPLI"; // Replace with your API key

        StockWrapperService stockService = new StockWrapperService(apiKey);
        JSONObject stockData = stockService.getStockData(stockTicker);

        if (stockData != null) {
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(stockData.toString());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to retrieve stock data.");
        }
    }
}


