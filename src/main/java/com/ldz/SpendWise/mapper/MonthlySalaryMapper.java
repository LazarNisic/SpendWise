package com.ldz.SpendWise.mapper;

import com.ldz.SpendWise.dto.MonthlySalaryDTO;
import com.ldz.SpendWise.model.MonthlySalary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MonthlySalaryMapper extends EntityMapper<MonthlySalaryDTO, MonthlySalary> {
}
