package com.ldz.SpendWise.controller;

import com.ldz.SpendWise.dto.CostCategoryDTO;
import com.ldz.SpendWise.service.CostCategoryService;
import com.ldz.SpendWise.service.data.CostCategoryData;
import com.ldz.SpendWise.service.data.Filter;
import com.ldz.SpendWise.service.data.SortOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping(value = "/api/cost-categories")
@RequiredArgsConstructor
@Validated
@Tag(name = "02 Cost Categories", description = "List of Cost Categories interfaces")
@SecurityRequirement(name = "bearerAuth")
public class CostCategoryController {
    private final CostCategoryService costCategoryService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CostCategoryDTO> findById(@Parameter(description = "Cost category ID") @PathVariable Long id) {
        return new ResponseEntity<>(costCategoryService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/find-by-name")
    public ResponseEntity<CostCategoryDTO> findByName(@RequestParam(defaultValue = StringUtils.EMPTY) String name) {
        return new ResponseEntity<>(costCategoryService.findByName(name), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Create new cost category", description = "Method for creating new cost category")
    @PostMapping
    public ResponseEntity<CostCategoryDTO> create(@Valid @RequestBody CostCategoryData costCategoryData) {
        return new ResponseEntity<>(costCategoryService.create(costCategoryData), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Update cost category", description = "Method for updating cost category")
    @PutMapping(value = "/{id}")
    public ResponseEntity<CostCategoryDTO> update(@Parameter(description = "Cost category ID") @PathVariable Long id,
                                                  @Valid @RequestBody CostCategoryData costCategoryData) {
        return new ResponseEntity<>(costCategoryService.update(id, costCategoryData), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Delete cost category", description = "Method for deleting cost category")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Cost category ID") @PathVariable Long id) {
        costCategoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Get all cost categories by filter", description = "Method for getting cost categories by filter")
    @GetMapping(value = "/search")
    public ResponseEntity<Page<CostCategoryDTO>> findCostCategoriesByFilter(@RequestParam(defaultValue = StringUtils.EMPTY) String search,
                                                           @RequestParam(defaultValue = "0") int pageNumber,
                                                           @RequestParam(defaultValue = "20") int pageSize,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection) {
        SortOrder sortOrder = new SortOrder(sortBy, sortDirection);
        return new ResponseEntity<>(costCategoryService.findAllByFilter(new Filter(search, pageNumber, pageSize, Collections.singletonList(sortOrder))), HttpStatus.OK);
    }
}
