package com.example.analyticsapp.stockwrapper;

import com.example.analyticsapp.common.ApiResponse;
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
    private String apiKey = "JGDUWG0S98HIRRTV";

    @GetMapping("/dailyStock/{stockTicker}")
    public ResponseEntity<?> getStock(@PathVariable String stockTicker) {
        try {
            StockWrapperService stockService = new StockWrapperService(apiKey);
            List<Map<String, String>> result = stockService.getDailyStockData(stockTicker);

            ApiResponse<List<Map<String, String>>> response = new ApiResponse<>(HttpStatus.OK.value(), "Success", result);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (TickerNotFoundException e) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Ticker not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (MalformedURLException e) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Bad request", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (IOException e) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Bad request", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/monthlyStock/{stockTicker}")
    public ResponseEntity<?> getMonthlyStock(@PathVariable String stockTicker) {
        try {
            StockWrapperService stockService = new StockWrapperService(apiKey);
            List<Map<String, String>> result = stockService.getMonthlyStockData(stockTicker);

            ApiResponse<List<Map<String, String>>> response = new ApiResponse<>(HttpStatus.OK.value(), "Success", result);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (TickerNotFoundException e) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Ticker not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (MalformedURLException e) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Bad request", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (IOException e) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Bad request", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @GetMapping("/stockSearch/{search}")
    public ResponseEntity<?> searchStocks(@PathVariable String search) {
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

    @GetMapping("/getStockListing")
    public ResponseEntity<?> getStockListing() {
        try {
            StockWrapperService stockService = new StockWrapperService(apiKey);
            List<Map<String, String>> result = stockService.getStockListing();

            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // @GetMapping("/getStockListingCSV")
    public ResponseEntity<?> getStockListingCSV() {
        try {
            StockWrapperService stockService = new StockWrapperService(apiKey);
            stockService.getStockListingCSV();

            System.out.println("CSV file created");
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("CSV file created");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
