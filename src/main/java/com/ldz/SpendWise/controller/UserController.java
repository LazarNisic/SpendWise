package com.ldz.SpendWise.controller;

import com.ldz.SpendWise.dto.UserDTO;
import com.ldz.SpendWise.enums.Role;
import com.ldz.SpendWise.service.UserService;
import com.ldz.SpendWise.service.data.ChangePasswordRequest;
import com.ldz.SpendWise.service.data.Filter;
import com.ldz.SpendWise.service.data.SortOrder;
import com.ldz.SpendWise.service.data.UserData;
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
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "01_Users", description = "List of User interfaces")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@Parameter(description = "User ID") @PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/authenticated-user")
    public ResponseEntity<UserDTO> getAuthenticatedUserDetails() {
        return new ResponseEntity<>(userService.getAuthenticatedUser(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @PostMapping(value = "/change-password")
    public ResponseEntity<UserDTO> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return new ResponseEntity<>(userService.changePassword(changePasswordRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Get all users by filter", description = "Method for getting users by filter")
    @GetMapping(value = "/search")
    public ResponseEntity<Page<UserDTO>> findUsersByFilter(@RequestParam(defaultValue = StringUtils.EMPTY) String search,
                                                           @RequestParam(defaultValue = "0") int pageNumber,
                                                           @RequestParam(defaultValue = "20") int pageSize,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection) {
        SortOrder sortOrder = new SortOrder(sortBy, sortDirection);
        return new ResponseEntity<>(userService.findAllByFilter(new Filter(search, pageNumber, pageSize, Collections.singletonList(sortOrder))), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Kreiranje novog korisnika sistema", description = "Metoda za kreiranje novog korisnika sistema")
    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserData userData) {
        return new ResponseEntity<>(userService.create(userData), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Promena podataka o korisniku sistema", description = "Metoda za promenu podataka o korisniku sistema")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@Parameter(description = "User ID") @PathVariable Long id,
                                          @Valid @RequestBody UserData userData) {
        return new ResponseEntity<>(userService.update(id, userData), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Dodavanje novih rola korisniku", description = "Metoda za dodavanje novih rola korisniku")
    @PostMapping(value = "/{id}/roles")
    public ResponseEntity<UserDTO> addRoles(@Parameter(description = "User ID") @PathVariable Long id,
                                            @Valid @RequestBody List<Role> roles) {
        return new ResponseEntity<>(userService.addRoles(id, roles), HttpStatus.OK);
    }
}
