package com.ldz.SpendWise.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class CostCategoryDTO implements Serializable {
    private Long id;
    private UserDTO user;
    private String name;
    private String searchKey;
}
