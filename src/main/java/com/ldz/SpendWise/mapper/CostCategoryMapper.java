package com.ldz.SpendWise.mapper;

import com.ldz.SpendWise.dto.CostCategoryDTO;
import com.ldz.SpendWise.model.CostCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CostCategoryMapper extends EntityMapper<CostCategoryDTO, CostCategory> {
}
