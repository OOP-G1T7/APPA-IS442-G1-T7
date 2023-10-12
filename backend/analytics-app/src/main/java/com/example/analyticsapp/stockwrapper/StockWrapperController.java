package com.example.analyticsapp.stockwrapper;

import java.time.LocalDate;
import java.util.*;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/stockwrapper")
public class StockWrapperController {
    @GetMapping("/{stockTicker}")
    public ResponseEntity<List<Map<String, String>>> getStock(@PathVariable String stockTicker) {
        String apiKey = "QJOEZUUAALL4GPLI"; // Replace with your API key

        StockWrapperService stockService = new StockWrapperService(apiKey);
        JSONObject stockData = stockService.getStockData(stockTicker);

        if (stockData != null) {
            List<Map<String, String>> result = new ArrayList<>();

            // Extract the relevant time series data based on the specified timeframe
            JSONObject timeSeries = stockData.getJSONObject("Monthly Time Series");

            // Create a TreeMap to automatically sort the data by date
            TreeMap<LocalDate, String> sortedData = new TreeMap<>();

            for (String dateStr : timeSeries.keySet()) {
                JSONObject timeData = timeSeries.getJSONObject(dateStr);
                String closingPrice = timeData.getString("4. close");

                // Parse the date string into a LocalDate object
                LocalDate date = LocalDate.parse(dateStr);

                sortedData.put(date, closingPrice);
            }

            // Convert the sorted data into the desired format
            for (Map.Entry<LocalDate, String> entry : sortedData.entrySet()) {
                Map<String, String> dataPoint = new HashMap<>();

                // Format the date as a string (if needed)
                String formattedDate = entry.getKey().toString();

                dataPoint.put(formattedDate, entry.getValue());
                result.add(dataPoint);
            }

            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.emptyList());
        }
    }

}


