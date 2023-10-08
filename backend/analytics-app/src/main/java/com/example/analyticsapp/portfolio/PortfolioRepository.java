package com.example.analyticsapp.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.analyticsapp.portfolio.PortfolioEntity;


@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {
    @Query(value = "SELECT * FROM portfolio where portfolio_id = ?", nativeQuery = true)
    PortfolioEntity getPortfolio(int portfolioId);
}
