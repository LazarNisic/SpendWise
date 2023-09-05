package com.ldz.SpendWise.service.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostData {
    @NotBlank(message = "Cost category name can not be empty")
    private String costCategory;
    @NotNull
    private Double amount;
    private LocalDateTime date;

}
