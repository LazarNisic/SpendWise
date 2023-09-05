package com.ldz.SpendWise.util;

import com.ldz.SpendWise.dto.UserDTO;
import com.ldz.SpendWise.enums.Role;
import com.ldz.SpendWise.exception.ValidationException;
import com.ldz.SpendWise.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserHelper {

    private final UserService userService;

    public void checkAuthenticatedUser(Long userId) {
        UserDTO authenticatedUser = userService.getAuthenticatedUser();
        if (!authenticatedUser.getId().equals(userId) && !authenticatedUser.getRoles().contains(Role.ADMIN)) {
            throw new ValidationException(String.format("Authenticated User id=[%s] is not eligible for this action", userId));
        }
    }
}
