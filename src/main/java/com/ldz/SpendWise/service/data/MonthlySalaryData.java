package com.ldz.SpendWise.service.data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlySalaryData {
    @NotNull
    private Double amount;
    @Min(1)
    @Max(31)
    private Integer dayOfMonth;
}
