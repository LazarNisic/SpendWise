package com.ldz.SpendWise.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class BalanceDTO implements Serializable {
    private Long id;
    private UserDTO user;
    private Double amount;
    private LocalDateTime timestamp;
}
