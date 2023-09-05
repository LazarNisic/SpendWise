package com.ldz.SpendWise.repository;

import com.ldz.SpendWise.model.CostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CostCategoryRepository extends JpaRepository<CostCategory, Long> {
    Optional<CostCategory> findByName(String name);

    Page<CostCategory> findCostCategoriesBySearchKeyContains(String stripAccents, PageRequest of);
}
