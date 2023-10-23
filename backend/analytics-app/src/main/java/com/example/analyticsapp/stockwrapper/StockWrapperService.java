package com.example.analyticsapp.stockwrapper;

import com.example.analyticsapp.stockwrapper.util.TickerNotFoundException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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

    public List<Map<String, String>> getDailyStockData(String stockTicker)
            throws Exception {
        List<Map<String, String>> result = new ArrayList<>();

        // Configure the Jedis connection pool for your Redis Cloud instance
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(poolConfig, "redis-11769.c292.ap-southeast-1-1.ec2.cloud.redislabs.com",
                11769);

        // username and password
        String username = "default";
        String password = "kkiwZNWzNfnH2OJH1xduyaGj1wbIt9K7";

        JSONObject stockData;

        Jedis jedis = jedisPool.getResource();

        // Authenticate to the Redis Cloud instance using your username and password
        jedis.auth(username, password);

        // Retrieve cached data
        String cachedData = jedis.get(stockTicker);

        // Check if it has been cached
        if (cachedData != null) {
            System.out.println("Data being fetched from Cached memory");
            stockData = new JSONObject(cachedData.toString());
            jedisPool.close();
        } else {
            // Data is not in the cache, fetch it from the original source
            System.out.println("Date being fetched from Alpha Vantage");

            String apiUrl = getStockEndpoint("data - " + stockTicker, "TIME_SERIES_DAILY");

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
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()))) {

                        StringBuilder response = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        // Parse the JSON response
                        stockData = new JSONObject(response.toString());

                        // Cache the response
                        jedis.setex("data - " + stockTicker, 3600, response.toString()); // Cache it for future use
                    }
                } else {
                    System.out.println("API request failed with status code: " + responseCode);
                    // NEED TO THROW ERROR
                    throw new Exception();
                }
            } else {
                System.out.println("Failed to retrieve API endpoint.");
                // NEED TO THROW ERROR
                throw new Exception();
            }

        }

        // API returned an error message
        if (stockData.has("Error Message")) {
            throw new TickerNotFoundException(stockTicker);
        }

        JSONObject timeSeries = stockData.getJSONObject("Time Series (Daily)");

        // Prepare data to return
        for (String dateStr : timeSeries.keySet()) {
            JSONObject timeData = timeSeries.getJSONObject(dateStr);
            String closingPrice = timeData.getString("4. close");

            Map<String, String> dataPoint = new HashMap<>();
            dataPoint.put("date", dateStr);
            dataPoint.put("close", closingPrice);
            result.add(dataPoint);
        }

        return result;
    }

    public List<Map<String, String>> searchStocks(String search) throws Exception {
        List<Map<String, String>> result = new ArrayList<>();

        // Configure the Jedis connection pool for your Redis Cloud instance
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(poolConfig, "redis-11769.c292.ap-southeast-1-1.ec2.cloud.redislabs.com",
                11769);

        // username and password
        String username = "default";
        String password = "kkiwZNWzNfnH2OJH1xduyaGj1wbIt9K7";

        Jedis jedis = jedisPool.getResource();

        // Authenticate to the Redis Cloud instance using your username and password
        jedis.auth(username, password);

        JSONObject searchResult;

        // Retrieve cached data
        String cachedData = jedis.get("search - " + search);

        // Check if it has been cached
        if (cachedData != null) {
            System.out.println("Data being fetched from Cached memory");
            searchResult = new JSONObject(cachedData.toString());
            jedisPool.close();
        } else {
            // Data is not in the cache, fetch it from the original source
            System.out.println("Date being fetched from Alpha Vantage");

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
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()))) {
                        StringBuilder response = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        // Parse the JSON response
                        searchResult = new JSONObject(response.toString());

                        // Cache the response
                        jedis.setex("search - " + search, 3600, response.toString()); // Cache it for future use

                    }
                } else {
                    System.out.println("API request failed with status code: " + responseCode);
                    // NEED THROW
                    throw new Exception();
                }
            } else {
                System.out.println("Failed to retrieve API endpoint.");
                // NEED THROW
                throw new Exception();
            }
        }

        JSONArray bestMatches = searchResult.getJSONArray("bestMatches");

        for (int i = 0; i < bestMatches.length(); i++) {
            JSONObject stockInfo = bestMatches.getJSONObject(i);
            Map<String, String> stockData = new HashMap<>();
            stockData.put("symbol", stockInfo.getString("1. symbol"));
            stockData.put("name", stockInfo.getString("2. name"));
            result.add(stockData);
        }

        return result;
    }

    public String getStockEndpoint(String stockTicker, String function) {
        return "https://www.alphavantage.co/query?function=" + function + "&symbol=" + stockTicker + "&apikey="
                + apiKey;
    }

    public String getSearchEndpoint(String search) {
        return "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=" + search + "&apikey=" + apiKey;
    }
}
