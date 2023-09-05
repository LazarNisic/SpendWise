package com.ldz.SpendWise.service.impl;

import com.ldz.SpendWise.dto.CostCategoryDTO;
import com.ldz.SpendWise.exception.CostCategoryNotFound;
import com.ldz.SpendWise.mapper.CostCategoryMapper;
import com.ldz.SpendWise.mapper.UserMapper;
import com.ldz.SpendWise.model.CostCategory;
import com.ldz.SpendWise.repository.CostCategoryRepository;
import com.ldz.SpendWise.service.CostCategoryService;
import com.ldz.SpendWise.service.UserService;
import com.ldz.SpendWise.service.data.CostCategoryData;
import com.ldz.SpendWise.service.data.Filter;
import com.ldz.SpendWise.util.AuthenticatedUserHelper;
import com.ldz.SpendWise.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CostCategoryServiceImpl implements CostCategoryService {

    private final CostCategoryRepository costCategoryRepository;
    private final CostCategoryMapper costCategoryMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticatedUserHelper authenticatedUserHelper;

    private final SortUtil sortUtil;

    @Override
    @Transactional(readOnly = true)
    public CostCategoryDTO findById(Long id) {
        CostCategory costCategory = costCategoryRepository.findById(id).orElseThrow(() -> new CostCategoryNotFound(id));
        return costCategoryMapper.toDto(costCategory);
    }

    @Override
    @Transactional(readOnly = true)
    //cache this method
    public CostCategoryDTO findByName(String name) {
        CostCategory costCategory = costCategoryRepository.findByName(name).orElseThrow(() -> new CostCategoryNotFound(name));
        return costCategoryMapper.toDto(costCategory);
    }

    @Override
    @Transactional
    public CostCategoryDTO create(CostCategoryData costCategoryData) {
        CostCategory costCategory = new CostCategory();
        costCategory.setUser(userMapper.toEntity(userService.getAuthenticatedUser()));
        costCategory.setName(costCategoryData.getName().toLowerCase(Locale.ROOT));
        costCategory.setSearchKey(StringUtils.stripAccents((costCategoryData.getName()).toLowerCase(Locale.ROOT)));
        return costCategoryMapper.toDto(costCategoryRepository.save(costCategory));
    }

    @Override
    @Transactional
    public CostCategoryDTO update(Long id, CostCategoryData costCategoryData) {
        CostCategory costCategory = costCategoryRepository.findById(id).orElseThrow(() -> new CostCategoryNotFound(id));
        authenticatedUserHelper.checkAuthenticatedUser(costCategory.getUser().getId());
        costCategory.setName(costCategoryData.getName().toLowerCase(Locale.ROOT));
        costCategory.setSearchKey(StringUtils.stripAccents((costCategoryData.getName()).toLowerCase(Locale.ROOT)));
        return costCategoryMapper.toDto(costCategoryRepository.save(costCategory));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CostCategory costCategory = costCategoryRepository.findById(id).orElseThrow(() -> new CostCategoryNotFound(id));
        authenticatedUserHelper.checkAuthenticatedUser(costCategory.getUser().getId());
        costCategoryRepository.deleteById(costCategory.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CostCategoryDTO> findAllByFilter(Filter filter) {
        //todo: check authenticated user
        Page<CostCategory> costCategories = costCategoryRepository.findCostCategoriesBySearchKeyContains(
                StringUtils.stripAccents(filter.getSearch().trim().toLowerCase(Locale.ROOT)),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize(), Sort.by(sortUtil.getSort(filter.getSort())))
        );
        return costCategories.map(costCategoryMapper::toDto);
    }
}
