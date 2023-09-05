package com.ldz.SpendWise.service.data;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageSortFilter extends PageFilter {
    private List<SortOrder> sort;

    public PageSortFilter(@PositiveOrZero(message = "Page Number must be positive or zero") int pageNumber,
                          @PositiveOrZero(message = "Page Size must be positive or zero") int pageSize,
                          List<SortOrder> sort) {
        super(pageNumber, pageSize);
        this.sort = sort;
    }
}
