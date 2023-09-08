package com.ldz.SpendWise.mapper;

import com.ldz.SpendWise.dto.BalanceDTO;
import com.ldz.SpendWise.model.Balance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BalanceMapper extends EntityMapper<BalanceDTO, Balance> {
}
