package com.ldz.SpendWise.controller;

import com.ldz.SpendWise.dto.BalanceDTO;
import com.ldz.SpendWise.service.BalanceService;
import com.ldz.SpendWise.service.data.BalanceData;
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
@RequestMapping(value = "/api/balance")
@RequiredArgsConstructor
@Validated
@Tag(name = "06 Balance", description = "List of Balance interfaces")
@SecurityRequirement(name = "bearerAuth")
public class BalanceController {

    private final BalanceService balanceService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<BalanceDTO> findById(@Parameter(description = "Balance ID") @PathVariable Long id) {
        return new ResponseEntity<>(balanceService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Create new balance", description = "Method for creating new balance")
    @PostMapping
    public ResponseEntity<BalanceDTO> create(@Valid @RequestBody BalanceData balanceData) {
        return new ResponseEntity<>(balanceService.create(balanceData), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Update balance", description = "Method for updating balance")
    @PutMapping(value = "/{id}")
    public ResponseEntity<BalanceDTO> update(@Parameter(description = "Balance ID") @PathVariable Long id,
                                             @Valid @RequestBody BalanceData balanceData) {
        return new ResponseEntity<>(balanceService.update(id, balanceData), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Delete balance", description = "Method for deleting balance")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Balance ID") @PathVariable Long id) {
        balanceService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "find-balance-for-user/{userId}")
    public ResponseEntity<BalanceDTO> findBalanceForUser(@Parameter(description = "User ID") @PathVariable Long userId) {
        return new ResponseEntity<>(balanceService.findBalanceForUser(userId), HttpStatus.OK);
    }

    //todo: delete this controller later
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "receive-salary")
    public ResponseEntity<Void> receiveSalary() {
        balanceService.receiveSalary();
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Add balance amount", description = "Method for adding balance amount")
    @PutMapping(value = "add-balance-amount/{id}")
    public ResponseEntity<BalanceDTO> addBalanceAmount(@Parameter(description = "Balance ID") @PathVariable Long id,
                                                       @Valid @RequestBody Double amount) {
        return new ResponseEntity<>(balanceService.addBalanceAmount(id, amount), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Subtract balance amount", description = "Method for subtracting balance amount")
    @PutMapping(value = "subtract-balance-amount/{id}")
    public ResponseEntity<BalanceDTO> subtractBalanceAmount(@Parameter(description = "Balance ID") @PathVariable Long id,
                                                            @Valid @RequestBody Double amount) {
        return new ResponseEntity<>(balanceService.subtractBalanceAmount(id, amount), HttpStatus.OK);
    }
}
