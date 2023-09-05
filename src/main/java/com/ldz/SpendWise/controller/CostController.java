package com.ldz.SpendWise.controller;

import com.ldz.SpendWise.dto.CostDTO;
import com.ldz.SpendWise.service.CostService;
import com.ldz.SpendWise.service.UserService;
import com.ldz.SpendWise.service.data.CostData;
import com.ldz.SpendWise.service.data.TotalCostForCostCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/cost")
@RequiredArgsConstructor
@Validated
@Tag(name = "03 Cost", description = "List of Cost interfaces")
@SecurityRequirement(name = "bearerAuth")
public class CostController {
    private final CostService costService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CostDTO> findById(@Parameter(description = "Cost ID") @PathVariable Long id) {
        return new ResponseEntity<>(costService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Create new cost", description = "Method for creating new cost")
    @PostMapping
    public ResponseEntity<CostDTO> create(@Valid @RequestBody CostData costData) {
        return new ResponseEntity<>(costService.create(costData), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Update cost", description = "Method for updating cost")
    @PutMapping(value = "/{id}")
    public ResponseEntity<CostDTO> update(@Parameter(description = "Cost ID") @PathVariable Long id,
                                          @Valid @RequestBody CostData costData) {
        return new ResponseEntity<>(costService.update(id, costData), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Delete cost", description = "Method for deleting cost")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Cost ID") @PathVariable Long id) {
        costService.delete(id);
        return ResponseEntity.ok().build();
    }

    //Check if there is better solution for DateTimeFormat, use @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) instead
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Get total cost for date interval", description = "Method for getting total cost for date interval")
    @GetMapping(value = "/total-cost")
    public ResponseEntity<Double> findTotalCost(@RequestParam("start")
                                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
                                                @RequestParam("end")
                                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {
        return new ResponseEntity<>(costService.findTotalCost(userService.getAuthenticatedUser().getId(), startDate, endDate), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Get total cost for date", description = "Method for getting total cost for date")
    @GetMapping(value = "/total-cost-for-date")
    public ResponseEntity<Double> findTotalCostForDate(@RequestParam("date")
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime date) {
        return new ResponseEntity<>(costService.findTotalCostForDate(userService.getAuthenticatedUser().getId(), date), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Get total cost for month", description = "Method for getting total cost for month")
    @GetMapping(value = "/total-cost-for-month")
    public ResponseEntity<Double> findTotalCostForMonth(@RequestParam("month") Integer month,
                                                        @RequestParam("year") Integer year) {
        return new ResponseEntity<>(costService.findTotalCostForMonth(userService.getAuthenticatedUser().getId(), month, year), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Get total cost for cost category for date interval", description = "Method for getting total cost for cost category for date interval")
    @GetMapping(value = "/total-cost-for-cost-category")
    public ResponseEntity<TotalCostForCostCategoryResponse> findTotalCostForCostCategory(@RequestParam("start")
                                                                                         @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
                                                                                         @RequestParam("end")
                                                                                         @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate,
                                                                                         @RequestParam("Cost category name") String costCategoryName) {
        return new ResponseEntity<>(costService.findTotalCostForCostCategory(userService.getAuthenticatedUser().getId(), startDate, endDate, costCategoryName), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Get total cost for cost category for date", description = "Method for getting total cost for cost category for date")
    @GetMapping(value = "/total-cost-for-cost-category-for-date")
    public ResponseEntity<TotalCostForCostCategoryResponse> findTotalCostForCostCategoryForDate(@RequestParam("date")
                                                                                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime date,
                                                                                                @RequestParam("Cost category name") String costCategoryName) {
        return new ResponseEntity<>(costService.findTotalCostForCostCategoryForDate(userService.getAuthenticatedUser().getId(), date, costCategoryName), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Get total cost for cost category for month", description = "Method for getting total cost for cost category for month")
    @GetMapping(value = "/total-cost-for-cost-category-for-month")
    public ResponseEntity<TotalCostForCostCategoryResponse> findTotalCostForCostCategoryForMonth(@RequestParam("month") Integer month,
                                                                                                 @RequestParam("year") Integer year,
                                                                                                 @RequestParam("Cost category name") String costCategoryName) {
        return new ResponseEntity<>(costService.findTotalCostForCostCategoryForMonth(userService.getAuthenticatedUser().getId(), month, year, costCategoryName), HttpStatus.OK);
    }
}
