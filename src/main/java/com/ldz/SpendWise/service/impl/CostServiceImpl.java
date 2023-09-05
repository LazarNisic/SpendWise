package com.ldz.SpendWise.service.impl;


import com.ldz.SpendWise.dto.CostDTO;
import com.ldz.SpendWise.exception.CostCategoryNotFound;
import com.ldz.SpendWise.exception.CostNotFound;
import com.ldz.SpendWise.mapper.CostCategoryMapper;
import com.ldz.SpendWise.mapper.CostMapper;
import com.ldz.SpendWise.model.Cost;
import com.ldz.SpendWise.model.CostCategory;
import com.ldz.SpendWise.repository.CostCategoryRepository;
import com.ldz.SpendWise.repository.CostRepository;
import com.ldz.SpendWise.service.CostCategoryService;
import com.ldz.SpendWise.service.CostService;
import com.ldz.SpendWise.service.data.CostCategoryData;
import com.ldz.SpendWise.service.data.CostData;
import com.ldz.SpendWise.service.data.TotalCostForCostCategoryResponse;
import com.ldz.SpendWise.util.AuthenticatedUserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CostServiceImpl implements CostService {

    private final CostRepository costRepository;
    private final CostMapper costMapper;
    private final CostCategoryRepository costCategoryRepository;
    private final CostCategoryService costCategoryService;
    private final CostCategoryMapper costCategoryMapper;
    private final AuthenticatedUserHelper authenticatedUserHelper;

    @Autowired
    @Lazy
    private CostService self;


    @Override
    @Transactional(readOnly = true)
    public CostDTO findById(Long id) {
        Cost cost = costRepository.findById(id).orElseThrow(() -> new CostNotFound(id));
        return costMapper.toDto(cost);
    }

    @Override
    @Transactional
    public CostDTO create(CostData costData) {
        Cost cost = new Cost();
        //should use costCategoryService instead?
        Optional<CostCategory> costCategoryOptional = costCategoryRepository.findByName(costData.getCostCategory().toLowerCase(Locale.ROOT));
        if (costCategoryOptional.isEmpty()) {
            cost.setCostCategory(costCategoryMapper.toEntity(costCategoryService.create(new CostCategoryData(costData.getCostCategory()))));
        } else {
            cost.setCostCategory(costCategoryOptional.get());
        }
        cost.setAmount(costData.getAmount());
        cost.setDate(LocalDateTime.now());
        cost.setTimestamp(LocalDateTime.now());
        return costMapper.toDto(costRepository.save(cost));
    }

    @Override
    @Transactional
    public CostDTO update(Long id, CostData costData) {
        Cost cost = costRepository.findById(id).orElseThrow(() -> new CostNotFound(id));
        authenticatedUserHelper.checkAuthenticatedUser(cost.getCostCategory().getUser().getId());
        Optional<CostCategory> costCategoryOptional = costCategoryRepository.findByName(costData.getCostCategory().toLowerCase(Locale.ROOT));
        if (costCategoryOptional.isEmpty()) {
            cost.setCostCategory(costCategoryMapper.toEntity(costCategoryService.create(new CostCategoryData(costData.getCostCategory()))));
        } else {
            cost.setCostCategory(costCategoryOptional.get());
        }
        cost.setAmount(costData.getAmount());
        Optional.ofNullable(costData.getDate()).ifPresent(cost::setDate);
        cost.setTimestamp(LocalDateTime.now());
        return costMapper.toDto(costRepository.save(cost));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cost cost = costRepository.findById(id).orElseThrow(() -> new CostNotFound(id));
        authenticatedUserHelper.checkAuthenticatedUser(cost.getCostCategory().getUser().getId());
        costRepository.deleteById(cost.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Double findTotalCost(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return costRepository.findTotalCost(userId, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public Double findTotalCostForDate(Long userId, LocalDateTime date) {
        return costRepository.findTotalCost(userId, date.toLocalDate().atStartOfDay(), date.toLocalDate().atStartOfDay().plusHours(24));
    }

    @Override
    @Transactional(readOnly = true)
    public Double findTotalCostForMonth(Long userId, Integer month, Integer year) {
        return costRepository.findTotalCost(userId, LocalDateTime.of(year, month, 1, 0, 0, 0), LocalDateTime.of(year, month, 1, 0, 0, 0).plusMonths(1));
    }

    @Override
    @Transactional(readOnly = true)
    public TotalCostForCostCategoryResponse findTotalCostForCostCategory(Long userId, LocalDateTime startDate, LocalDateTime endDate, String costCategoryName) {
        //should use costCategoryService instead?
        costCategoryRepository.findByName(costCategoryName).orElseThrow(() -> new CostCategoryNotFound(costCategoryName));
        Double totalCostCategoryAmount = costRepository.findTotalCostForCostCategory(userId, startDate, endDate, costCategoryName);
        Double totalCostPercentage = totalCostCategoryAmount / self.findTotalCost(userId, startDate, endDate) * 100;
        Double roundedTotalCostPercentage = Math.round(totalCostPercentage * 100.0) / 100.0;
        return new TotalCostForCostCategoryResponse(totalCostCategoryAmount, roundedTotalCostPercentage);
    }

    @Override
    @Transactional(readOnly = true)
    public TotalCostForCostCategoryResponse findTotalCostForCostCategoryForDate(Long userId, LocalDateTime date, String costCategoryName) {
        costCategoryRepository.findByName(costCategoryName).orElseThrow(() -> new CostCategoryNotFound(costCategoryName));
        Double totalCostCategoryAmount = costRepository.findTotalCostForCostCategory(userId, date.toLocalDate().atStartOfDay(), date.toLocalDate().atStartOfDay().plusHours(24), costCategoryName);
        Double totalCostPercentage = totalCostCategoryAmount / self.findTotalCostForDate(userId, date) * 100;
        Double roundedTotalCostPercentage = Math.round(totalCostPercentage * 100.0) / 100.0;
        return new TotalCostForCostCategoryResponse(totalCostCategoryAmount, roundedTotalCostPercentage);
    }

    @Override
    @Transactional(readOnly = true)
    public TotalCostForCostCategoryResponse findTotalCostForCostCategoryForMonth(Long userId, Integer month, Integer year, String costCategoryName) {
        costCategoryRepository.findByName(costCategoryName).orElseThrow(() -> new CostCategoryNotFound(costCategoryName));
        Double totalCostCategoryAmount = costRepository.findTotalCostForCostCategory(userId, LocalDateTime.of(year, month, 1, 0, 0, 0), LocalDateTime.of(year, month, 1, 0, 0, 0).plusMonths(1), costCategoryName);
        Double totalCostPercentage = totalCostCategoryAmount / self.findTotalCostForMonth(userId, month, year) * 100;
        Double roundedTotalCostPercentage = Math.round(totalCostPercentage * 100.0) / 100.0;
        return new TotalCostForCostCategoryResponse(totalCostCategoryAmount, roundedTotalCostPercentage);
    }
}
