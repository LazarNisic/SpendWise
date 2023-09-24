package com.ldz.SpendWise.repository;

import com.ldz.SpendWise.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByUser_Id(Long userId);
}
