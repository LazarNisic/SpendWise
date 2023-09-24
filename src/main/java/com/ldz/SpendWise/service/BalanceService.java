package com.ldz.SpendWise.service;

import com.ldz.SpendWise.dto.BalanceDTO;
import com.ldz.SpendWise.service.data.BalanceData;

public interface BalanceService {
    BalanceDTO findById(Long id);

    BalanceDTO create(BalanceData balanceData);

    BalanceDTO update(Long id, BalanceData balanceData);

    void delete(Long id);

    BalanceDTO findBalanceForUser(Long userId);

    void receiveSalary();
}
