package com.ldz.SpendWise.service.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalCostForCostCategoryResponse {
    Double totalCostCategoryAmount;
    Double totalCostPercentage;
}
