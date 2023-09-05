package com.ldz.SpendWise.service.data;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageFilter {
    @PositiveOrZero(message = "Page Number must be positive or zero")
    private int pageNumber = 0;
    @PositiveOrZero(message = "Page Size must be positive or zero")
    private int pageSize = 2000;
}
