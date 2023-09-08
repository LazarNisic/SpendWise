package com.ldz.SpendWise.service;

import com.ldz.SpendWise.dto.UserDTO;
import com.ldz.SpendWise.enums.Role;
import com.ldz.SpendWise.service.data.ChangePasswordRequest;
import com.ldz.SpendWise.service.data.Filter;
import com.ldz.SpendWise.service.data.UserData;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    //todo: implement findAll method
    UserDTO findById(Long id);

    UserDTO findByUsername(String username);

    Page<UserDTO> findAllByFilter(Filter filter);

    UserDTO getAuthenticatedUser();

    UserDTO changePassword(ChangePasswordRequest changePasswordRequest);

    UserDTO create(UserData userData);

    UserDTO update(Long id, UserData userData);

    UserDTO addRoles(Long id, List<Role> roles);
}
