package com.ldz.SpendWise.mapper;

import com.ldz.SpendWise.dto.ExtraIncomeDTO;
import com.ldz.SpendWise.model.ExtraIncome;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExtraIncomeMapper extends EntityMapper<ExtraIncomeDTO, ExtraIncome> {
}
