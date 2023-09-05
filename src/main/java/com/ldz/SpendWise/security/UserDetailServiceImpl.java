package com.ldz.SpendWise.security;

import com.ldz.SpendWise.exception.UserIsNotActive;
import com.ldz.SpendWise.exception.UserNotFound;
import com.ldz.SpendWise.model.User;
import com.ldz.SpendWise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFound(username));
        if (Boolean.FALSE.equals(user.getEnabled())) {
            throw new UserIsNotActive(username);
        }
        return new UserPrincipal(user);
    }
}
