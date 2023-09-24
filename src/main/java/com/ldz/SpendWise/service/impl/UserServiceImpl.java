package com.ldz.SpendWise.service.impl;

import com.ldz.SpendWise.dto.UserDTO;
import com.ldz.SpendWise.enums.Role;
import com.ldz.SpendWise.exception.*;
import com.ldz.SpendWise.mapper.UserMapper;
import com.ldz.SpendWise.model.User;
import com.ldz.SpendWise.model.UserRole;
import com.ldz.SpendWise.repository.UserRepository;
import com.ldz.SpendWise.repository.UserRoleRepository;
import com.ldz.SpendWise.service.UserService;
import com.ldz.SpendWise.service.data.ChangePasswordRequest;
import com.ldz.SpendWise.service.data.Filter;
import com.ldz.SpendWise.service.data.UserData;
import com.ldz.SpendWise.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SortUtil sortUtil;

    private final UserRoleRepository userRoleRepository;

    //todo: cache this method
    @Override
    public List<UserDTO> findAll() {
        return userMapper.toDto(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByUsername(String username) {
        return userMapper.toDto(userRepository.findByUsername(username).orElseThrow(() -> new UserNotFound(username)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> findAllByFilter(Filter filter) {
        Page<User> users = userRepository.findUsersBySearchKeyContains(
                StringUtils.stripAccents(filter.getSearch().trim().toLowerCase(Locale.ROOT)),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize(), Sort.by(sortUtil.getSort(filter.getSort())))
        );
        return users.map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getAuthenticatedUser() {
        return findByUsername(getAuthenticationName());
    }

    @Override
    @Transactional
    public UserDTO changePassword(ChangePasswordRequest changePasswordRequest) {
        UserDTO userDTO = getAuthenticatedUser();
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new UserNotFound(userDTO.getId()));
        comparePasswords(changePasswordRequest);
        isOldPassword(user, changePasswordRequest.getOldPassword());
        return updatePassword(user, changePasswordRequest.getNewPassword());
    }

    @Override
    public UserDTO create(UserData userData) {
        User user = new User();
        setUserData(user, userData);
        return updatePassword(user, userData.getPassword());
    }

    @Override
    @Transactional
    public UserDTO update(Long id, UserData userData) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
        if (!userData.getUsername().equals(user.getUsername())) {
            throw new ChangeUsernameException();
        }
        setUserData(user, userData);
        if (StringUtils.isNotBlank(userData.getPassword())) {
            return updatePassword(user, userData.getPassword());
        } else {
            return userMapper.toDto(userRepository.save(user));
        }
    }

    @Override
    @Transactional
    public UserDTO addRoles(Long id, List<Role> roles) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));

        List<UserRole> oldUserRoles = user.getUserRoles().stream().filter(Predicate.not(userRole -> roles.contains(userRole.getRole()))).toList();
        List<Role> newRoles = roles.stream().filter(Predicate.not(user.getUserRoles().stream().map(UserRole::getRole).toList()::contains)).toList();

        user.getUserRoles().removeAll(oldUserRoles);
        userRepository.saveAndFlush(user);

        newRoles.forEach(newRole -> {
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(newRole);
            userRoleRepository.save(userRole);
        });
        return userMapper.toDto(user);
    }

    private void setUserData(User user, UserData userData) {
        user.setUsername(userData.getUsername());
        user.setFullName(userData.getFullName());
        user.setEnabled(true);
        user.setSearchKey(StringUtils.stripAccents((userData.getUsername() + " " + userData.getFullName()).toLowerCase(Locale.ROOT)));
    }

    private String getAuthenticationName() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new BadCredentials();
        }
        return authentication.getName();
    }

    private UserDTO updatePassword(User user, String password) {
        final String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        return userMapper.toDto(userRepository.save(user));
    }

    private void comparePasswords(ChangePasswordRequest changePasswordRequest) {
        if (changePasswordRequest.getOldPassword().equals(changePasswordRequest.getNewPassword())) {
            throw new PasswordsMatchException();
        }
    }

    private void isOldPassword(User user, String oldPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new OldPasswordException();
        }
    }
}
