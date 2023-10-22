package com.example.analyticsapp.stockwrapper;

import com.example.analyticsapp.stockwrapper.util.TickerNotFoundException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class StockWrapperService {
    private final String apiKey;

    public StockWrapperService(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<Map<String, String>> getDailyStockData(String stockTicker) throws TickerNotFoundException, MalformedURLException, IOException {
        List<Map<String, String>> result = new ArrayList<>();

        String apiUrl = getStockEndpoint(stockTicker, "TIME_SERIES_DAILY");

        if (apiUrl != null) {
            // Create a URL object
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response data
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Parse the JSON response
                    JSONObject stockData = new JSONObject(response.toString());

                    // API returned an error message
                    if (stockData.has("Error Message")) {
                        throw new TickerNotFoundException(stockTicker);
                    }

                    JSONObject timeSeries = stockData.getJSONObject("Time Series (Daily)");

                    for (String dateStr : timeSeries.keySet()) {
                        JSONObject timeData = timeSeries.getJSONObject(dateStr);
                        String closingPrice = timeData.getString("4. close");

                        Map<String, String> dataPoint = new HashMap<>();
                        dataPoint.put("date", dateStr);
                        dataPoint.put("close", closingPrice);
                        result.add(dataPoint);
                    }
                }
            } else {
                System.out.println("API request failed with status code: " + responseCode);
            }
        } else {
            System.out.println("Failed to retrieve API endpoint.");
        }

        return result;
    }

    public List<Map<String, String>> searchStocks(String search) throws Exception{
        List<Map<String, String>> result = new ArrayList<>();

        String apiUrl = getSearchEndpoint(search);

        if (apiUrl != null) {
            // Create a URL object
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response data
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Parse the JSON response
                    JSONObject searchResult = new JSONObject(response.toString());
                    JSONArray bestMatches = searchResult.getJSONArray("bestMatches");

                    for (int i = 0; i < bestMatches.length(); i++) {
                        JSONObject stockInfo = bestMatches.getJSONObject(i);
                        Map<String, String> stockData = new HashMap<>();
                        stockData.put("symbol", stockInfo.getString("1. symbol"));
                        stockData.put("name", stockInfo.getString("2. name"));
                        result.add(stockData);
                    }
                }
            } else {
                System.out.println("API request failed with status code: " + responseCode);
            }
        } else {
            System.out.println("Failed to retrieve API endpoint.");
        }
        return result;
    }

    public String getStockEndpoint(String stockTicker, String function) {
        return "https://www.alphavantage.co/query?function=" + function + "&symbol=" + stockTicker + "&apikey=" + apiKey;
    }

    public String getSearchEndpoint(String search) {
        return "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=" + search + "&apikey=" + apiKey;
    }
}
