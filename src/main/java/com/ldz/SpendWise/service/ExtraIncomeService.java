package com.ldz.SpendWise.service;

import com.ldz.SpendWise.dto.ExtraIncomeDTO;
import com.ldz.SpendWise.service.data.ExtraIncomeData;

import java.time.LocalDateTime;

public interface ExtraIncomeService {
    ExtraIncomeDTO findById(Long id);

    ExtraIncomeDTO create(ExtraIncomeData extraIncomeData);

    ExtraIncomeDTO update(Long id, ExtraIncomeData extraIncomeData);

    void delete(Long id);

    //todo: Implement findExtraIncomeForMonth or findExtraIncomeForInterval
    Double findTotalExtraIncomeForMonth(Long userId, Integer month, Integer year);
    Double findTotalExtraIncomeForInterval(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
