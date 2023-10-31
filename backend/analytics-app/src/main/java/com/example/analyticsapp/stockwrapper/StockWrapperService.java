package com.example.analyticsapp.stockwrapper;

import com.example.analyticsapp.redis.RedisService;
import com.example.analyticsapp.stockwrapper.util.TickerNotFoundException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class StockWrapperService {
    private final String apiKey;

    private RedisService redisService = new RedisService();

    public StockWrapperService(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<Map<String, String>> getDailyStockData(String stockTicker)
            throws Exception {
        List<Map<String, String>> result = new ArrayList<>();
        JSONObject stockData;

        // Retrieve cached data
        String cachedData = redisService.getCachedData("data - " + stockTicker);

        // Check if it has been cached
        if (cachedData != null) {
            System.out.println("Data being fetched from Cached memory");
            stockData = new JSONObject(cachedData.toString());
        } else {
            // Data is not in the cache, fetch it from the original source
            System.out.println("Date being fetched from Alpha Vantage");

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
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()))) {

                        StringBuilder response = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        // Parse the JSON response
                        stockData = new JSONObject(response.toString());

                        redisService.cacheData("data - " + stockTicker, response.toString());
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

        JSONObject searchResult;

        String cachedData = redisService.getCachedData("search - " + search);

        // Check if it has been cached
        if (cachedData != null) {
            System.out.println("Data being fetched from Cached memory");
            searchResult = new JSONObject(cachedData.toString());
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

                        redisService.cacheData("search - " + search, response.toString());

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

    public List<Map<String, String>> getStockListing() throws Exception {
        List<Map<String, String>> result = new ArrayList<>();

        String fileName = "backend/data/stockListing.csv";
        File csvFile = new File(fileName);

        if (!csvFile.exists()) {
            System.out.println("CSV file does not exist. Creating file...");
            getStockListingCSV();
        }

        try (Scanner sc = new Scanner(csvFile)) {
            // Skip the first line
            sc.nextLine();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] stockData = line.split(",");

                Map<String, String> stockInfo = new HashMap<>();
                stockInfo.put("symbol", stockData[0]);
                stockInfo.put("name", stockData[1]);
                stockInfo.put("exchange", stockData[2]);
                stockInfo.put("assetType", stockData[3]);
                result.add(stockInfo);
            }
        } catch (IOException e) {
            System.out.println("Failed to read CSV file: " + e.getMessage());
            throw e;
        }
        return result;
    }

    public void getStockListingCSV() throws Exception {
        String plannedPath = "backend";
        File plannedPathDir = new File(plannedPath);

        if (!plannedPathDir.exists()) {
            plannedPath = "..";
        }

        String fileName = plannedPath + "/data/stockListing.csv";
        File csvFile = new File(fileName);

        if (csvFile.exists()) {
            System.out.println("CSV file already exists.");

            Calendar calendar = Calendar.getInstance();
            Date today = calendar.getTime();

            // Get the first day of the current week (Sunday)
            calendar.setTime(today);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            long firstDayInMilliseconds = calendar.getTimeInMillis();

            // If the CSV file was created on the same week, do not update
            if (csvFile.lastModified() >= firstDayInMilliseconds) {
                System.out.println("CSV file was created this week. No need to update.");
                return;
            }
        }

        String apiUrl = getStockListingEndpoint();

        if (apiUrl != null) {
            try {
                // Create a URL object
                URL url = new URL(apiUrl);

                // Open a connection to the URL
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Get the response code
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    File dataFolder = new File(plannedPath + "/data");

                    if (!dataFolder.exists()) {
                        dataFolder.mkdir();
                    }

                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            FileWriter fileWriter = new FileWriter(csvFile, false)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            // Write the line to the CSV file
                            fileWriter.write(line);
                            fileWriter.write("\n");
                        }
                    } catch (IOException e) {
                        System.out.println("Failed to write to CSV file: " + e.getMessage());
                        throw e;
                    }
                } else {
                    System.out.println("API request failed with status code: " + responseCode);
                    throw new Exception("API request failed with status code: " + responseCode);
                }
            } catch (IOException e) {
                System.out.println("Failed to retrieve API endpoint.");
                throw e;
            }
        } else {
            System.out.println("Invalid API endpoint.");
            throw new Exception("Invalid API endpoint.");
        }
        return;
    }

    public String getStockEndpoint(String stockTicker, String function) {
        return "https://www.alphavantage.co/query?function=" + function + "&symbol=" + stockTicker + "&apikey="
                + apiKey;
    }

    public String getSearchEndpoint(String search) {
        return "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=" + search + "&apikey=" + apiKey;
    }

    public String getStockListingEndpoint() {
        return "https://www.alphavantage.co/query?function=LISTING_STATUS&state=active&apikey=" + apiKey;
    }
}
