package com.ldz.SpendWise.service;

import com.ldz.SpendWise.dto.CostDTO;
import com.ldz.SpendWise.service.data.CostData;
import com.ldz.SpendWise.service.data.TotalCostForCostCategoryResponse;

import java.time.LocalDateTime;

public interface CostService {
    CostDTO findById(Long id);

    CostDTO create(CostData costData);

    CostDTO update(Long id, CostData costData);

    void delete(Long id);

    Double findTotalCost(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    Double findTotalCostForDate(Long userId, LocalDateTime date);

    Double findTotalCostForMonth(Long userId, Integer month, Integer year);

    TotalCostForCostCategoryResponse findTotalCostForCostCategory(Long userId, LocalDateTime startDate, LocalDateTime endDate, String costCategoryName);

    TotalCostForCostCategoryResponse findTotalCostForCostCategoryForDate(Long userId, LocalDateTime date, String costCategoryName);

    TotalCostForCostCategoryResponse findTotalCostForCostCategoryForMonth(Long userId, Integer month, Integer year, String costCategoryName);

    //todo: Implement findAllByFilter method ??
}
