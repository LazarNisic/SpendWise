package com.ldz.SpendWise.repository;

import com.ldz.SpendWise.model.MonthlySalary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonthlySalaryRepository extends JpaRepository<MonthlySalary, Long> {
    Optional<MonthlySalary> findByUser_Id(Long userId);
}
