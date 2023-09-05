package com.ldz.SpendWise.repository;

import com.ldz.SpendWise.model.ExtraIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ExtraIncomeRepository extends JpaRepository<ExtraIncome, Long> {
    @Query("""
            SELECT SUM(e.amount) AS totalAmount
            FROM ExtraIncome e
            WHERE e.user.id = :userId AND e.date between :startDate AND :endDate
            """)
    Double findTotalExtraIncome(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
