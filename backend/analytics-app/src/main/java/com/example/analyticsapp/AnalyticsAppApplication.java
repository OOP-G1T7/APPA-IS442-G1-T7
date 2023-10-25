package com.example.analyticsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.analyticsapp.stockwrapper.StockWrapperController;

@SpringBootApplication
public class AnalyticsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsAppApplication.class, args);
		onStart();
	}

	public static void onStart() {
		StockWrapperController stockWrapperController = new StockWrapperController();
		stockWrapperController.getStockListingCSV();
	}

}
