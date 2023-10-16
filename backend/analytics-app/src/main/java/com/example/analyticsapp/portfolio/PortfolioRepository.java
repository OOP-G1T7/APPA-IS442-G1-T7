package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {

    @Query(value = "SELECT * FROM portfolio", nativeQuery = true)
    ArrayList<PortfolioEntity> getAllPortfolios();

    @Query(value = "SELECT * FROM portfolio WHERE user_id = ?", nativeQuery = true)
    ArrayList<PortfolioEntity> getAllPortfoliosByUserId(int userId);


    @Query(value = "SELECT * FROM portfolio WHERE portfolio_id = ?", nativeQuery = true)
    PortfolioEntity getPortfolio(int portfolioId);
}
