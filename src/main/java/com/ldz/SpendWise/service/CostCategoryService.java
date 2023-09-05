package com.ldz.SpendWise.service;

import com.ldz.SpendWise.dto.CostCategoryDTO;
import com.ldz.SpendWise.service.data.CostCategoryData;
import com.ldz.SpendWise.service.data.Filter;
import org.springframework.data.domain.Page;

public interface CostCategoryService {

    CostCategoryDTO findById(Long id);

    CostCategoryDTO findByName(String name);

    CostCategoryDTO create(CostCategoryData costCategoryData);

    CostCategoryDTO update(Long id, CostCategoryData costCategoryData);

    void delete(Long id);

    //todo: Implement findAllByFilter method
    Page<CostCategoryDTO> findAllByFilter(Filter filter);
}
