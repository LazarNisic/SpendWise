package com.ldz.SpendWise.service.data;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostCategoryData {
    @NotBlank(message = "Cost category name can not be empty")
    private String name;
}
