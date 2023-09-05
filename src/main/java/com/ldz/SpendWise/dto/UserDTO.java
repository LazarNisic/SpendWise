package com.ldz.SpendWise.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ldz.SpendWise.enums.Role;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@SuperBuilder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class UserDTO implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private Boolean enabled;
    private String searchKey;
    private List<Role> roles;
}
