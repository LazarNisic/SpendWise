package com.ldz.SpendWise.mapper;

import com.ldz.SpendWise.dto.CostDTO;
import com.ldz.SpendWise.model.Cost;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CostMapper extends EntityMapper<CostDTO, Cost> {
}
