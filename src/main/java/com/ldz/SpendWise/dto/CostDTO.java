package com.ldz.SpendWise.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class CostDTO implements Serializable {
    private Long id;
    private CostCategoryDTO costCategory;
    private Double amount;
    private LocalDateTime date;
    private LocalDateTime timestamp;
}
