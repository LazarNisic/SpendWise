package com.ldz.SpendWise.service.data;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtraIncomeData {
    @NotNull
    private Double amount;
    private LocalDateTime date;
}
