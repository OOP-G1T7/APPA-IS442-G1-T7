package com.example.analyticsapp.portfolio;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface StockRepository extends JpaRepository<StockEntity, StockPK>{
    @Query(value = "SELECT * FROM portfolio_stock where portfolio_id = ?", nativeQuery = true)
    ArrayList<StockEntity> getAllStocks(int portfolioId);

    @Query(value = "SELECT * FROM portfolio_stock where portfolio_id = ? and ticker = ?", nativeQuery = true)
    StockEntity getOneStock(int portfolio_id, String ticker);
}
