package com.example.analyticsapp.stockwrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class StockWrapperService {
    private String apiKey;

    public StockWrapperService(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getStockEndpoint(String stockTicker) {
        return "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + stockTicker + "&interval=15min&apikey=" + apiKey;
    }

    public JSONObject getStockData(String stockTicker) {
        try {
            String apiUrl = getStockEndpoint(stockTicker);

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
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse the JSON response
                    return new JSONObject(response.toString());
                } else {
                    System.out.println("API request failed with status code: " + responseCode);
                }

                // Close the connection
                connection.disconnect();
            } else {
                System.out.println("Failed to retrieve API endpoint.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
