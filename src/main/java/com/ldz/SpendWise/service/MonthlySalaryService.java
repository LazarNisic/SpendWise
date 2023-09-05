package com.ldz.SpendWise.service;

import com.ldz.SpendWise.dto.MonthlySalaryDTO;
import com.ldz.SpendWise.service.data.MonthlySalaryData;

public interface MonthlySalaryService {
    MonthlySalaryDTO findById(Long id);

    MonthlySalaryDTO create(MonthlySalaryData monthlySalaryData);

    MonthlySalaryDTO update(Long id, MonthlySalaryData monthlySalaryData);

    void delete(Long id);

    //todo: implement method findMonthlySalaryForUser
}
