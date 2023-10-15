package com.example.analyticsapp.portfolio;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public class StockRepository extends JpaRepository<StockEntity, String, Integer>{
    
}
