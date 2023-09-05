package com.ldz.SpendWise.service.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SortOrder {
    private String field;
    private Sort.Direction direction;
}
