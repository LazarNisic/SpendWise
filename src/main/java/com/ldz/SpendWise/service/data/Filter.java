package com.ldz.SpendWise.service.data;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Getter
public class Filter extends PageSortFilter {
    private String search = StringUtils.EMPTY;

    public Filter(String search,
                  @PositiveOrZero(message = "Page Number must be positive or zero") int pageNumber,
                  @PositiveOrZero(message = "Page Size must be positive or zero") int pageSize,
                  List<SortOrder> sort) {
        super(pageNumber, pageSize, sort);
        this.search = search;
    }
}
