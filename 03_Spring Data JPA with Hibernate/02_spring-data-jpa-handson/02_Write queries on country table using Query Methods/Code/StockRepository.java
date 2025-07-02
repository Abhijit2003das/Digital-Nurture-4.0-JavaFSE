package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Integer> {

    // 1. Facebook stock in Sep 2019
    List<Stock> findByCodeAndDateBetween(String code, LocalDate startDate, LocalDate endDate);

    // 2. Google stocks with close > 1250
    List<Stock> findByCodeAndCloseGreaterThan(String code, BigDecimal close);

    // 3. Top 3 highest volume
    List<Stock> findTop3ByOrderByVolumeDesc();

    // 4. Netflix lowest close prices
    List<Stock> findTop3ByCodeOrderByCloseAsc(String code);
}
