package com.ldz.SpendWise.repository;

import com.ldz.SpendWise.model.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface CostRepository extends JpaRepository<Cost, Long> {

    @Query("""
            SELECT SUM(c.amount) AS totalAmount
            FROM Cost c JOIN CostCategory cc
            ON c.costCategory.id = cc.id
            WHERE cc.user.id = :userId AND c.date between :startDate AND :endDate
            """)
    Double findTotalCost(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("""
            SELECT SUM(c.amount) AS totalCostCategoryAmount
            FROM Cost c JOIN CostCategory cc
            ON c.costCategory.id = cc.id
            WHERE cc.user.id = :userId AND c.date between :startDate AND :endDate
            AND cc.name = :costCategoryName
            """)
    Double findTotalCostForCostCategory(Long userId, LocalDateTime startDate, LocalDateTime endDate, String costCategoryName);
}
