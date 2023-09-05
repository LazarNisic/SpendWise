package com.ldz.SpendWise.mapper;

import com.ldz.SpendWise.dto.UserDTO;
import com.ldz.SpendWise.enums.Role;
import com.ldz.SpendWise.model.User;
import com.ldz.SpendWise.model.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
    @Override
    @Mapping(target = "roles", source = "userRoles", qualifiedByName = "userRolesToRoles")
    UserDTO toDto(User entity);

    @Override
    @Mapping(target = "roles", source = "userRoles", qualifiedByName = "userRolesToRoles")
    List<UserDTO> toDto(List<User> entityList);

    @Named("userRolesToRoles")
    default List<Role> userRolesToRoles(List<UserRole> userRoles) {
        if (userRoles == null) {
            return Collections.emptyList();
        }
        return userRoles
                .stream()
                .map(UserRole::getRole)
                .toList();
    }
}
