package com.ldz.SpendWise.controller;

import com.ldz.SpendWise.dto.MonthlySalaryDTO;
import com.ldz.SpendWise.service.MonthlySalaryService;
import com.ldz.SpendWise.service.data.MonthlySalaryData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/monthly-salary")
@RequiredArgsConstructor
@Validated
@Tag(name = "04 Monthly salary", description = "List of Monthly salary interfaces")
@SecurityRequirement(name = "bearerAuth")
public class MonthlySalaryController {

    private final MonthlySalaryService monthlySalaryService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<MonthlySalaryDTO> findById(@Parameter(description = "Monthly salary ID") @PathVariable Long id) {
        return new ResponseEntity<>(monthlySalaryService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Create new monthly salary", description = "Method for creating new monthly salary")
    @PostMapping
    public ResponseEntity<MonthlySalaryDTO> create(@Valid @RequestBody MonthlySalaryData monthlySalaryData) {
        return new ResponseEntity<>(monthlySalaryService.create(monthlySalaryData), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Update monthly salary", description = "Method for updating monthly salary")
    @PutMapping(value = "/{id}")
    public ResponseEntity<MonthlySalaryDTO> update(@Parameter(description = "Monthly salary ID") @PathVariable Long id,
                                                   @Valid @RequestBody MonthlySalaryData monthlySalaryData) {
        return new ResponseEntity<>(monthlySalaryService.update(id, monthlySalaryData), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Delete monthly salary", description = "Method for deleting monthly salary")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Monthly salary ID") @PathVariable Long id) {
        monthlySalaryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
