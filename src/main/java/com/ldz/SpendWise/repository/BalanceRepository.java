package com.ldz.SpendWise.repository;

import com.ldz.SpendWise.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
}
