package com.ldz.SpendWise.controller;

import com.ldz.SpendWise.dto.ExtraIncomeDTO;
import com.ldz.SpendWise.service.ExtraIncomeService;
import com.ldz.SpendWise.service.UserService;
import com.ldz.SpendWise.service.data.ExtraIncomeData;
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
@RequestMapping(value = "/api/extra-income")
@RequiredArgsConstructor
@Validated
@Tag(name = "05 Extra income", description = "List of Extra income interfaces")
@SecurityRequirement(name = "bearerAuth")
public class ExtraIncomeController {
    private final ExtraIncomeService extraIncomeService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ExtraIncomeDTO> findById(@Parameter(description = "Extra income ID") @PathVariable Long id) {
        return new ResponseEntity<>(extraIncomeService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Create new extra income", description = "Method for creating new extra income")
    @PostMapping
    public ResponseEntity<ExtraIncomeDTO> create(@Valid @RequestBody ExtraIncomeData extraIncomeData) {
        return new ResponseEntity<>(extraIncomeService.create(extraIncomeData), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Update extra income", description = "Method for updating extra income")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ExtraIncomeDTO> update(@Parameter(description = "Extra income ID") @PathVariable Long id,
                                                 @Valid @RequestBody ExtraIncomeData extraIncomeData) {
        return new ResponseEntity<>(extraIncomeService.update(id, extraIncomeData), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Delete extra income", description = "Method for deleting extra income")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Extra income ID") @PathVariable Long id) {
        extraIncomeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Get total extra income for month", description = "Method for getting total extra income for month")
    @GetMapping(value = "/total-extra-income-for-month")
    public ResponseEntity<Double> findTotalExtraIncomeForMonth(@RequestParam("month") Integer month,
                                                               @RequestParam("year") Integer year) {
        return new ResponseEntity<>(extraIncomeService.findTotalExtraIncomeForMonth(userService.getAuthenticatedUser().getId(), month, year), HttpStatus.OK);
    }

    //todo: add default value for start and end, this annotation does not work
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Get total extra income for date interval", description = "Method for getting total extra income for date interval")
    @GetMapping(value = "/total-extra-income-for-interval")
    public ResponseEntity<Double> findTotalExtraIncomeForInterval(@RequestParam(value = "start", required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}")
                                                                  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
                                                                  @RequestParam(value = "end", required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}")
                                                                  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {
        return new ResponseEntity<>(extraIncomeService.findTotalExtraIncomeForInterval(userService.getAuthenticatedUser().getId(), startDate, endDate), HttpStatus.OK);
    }
}
