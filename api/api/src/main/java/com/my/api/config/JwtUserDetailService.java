package com.my.api.config;

import com.my.api.model.UserLogin;
import com.my.api.repository.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    UserLoginRepository userLoginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserLogin userLogin = userLoginRepository.findByUsername(username);

        if (Objects.isNull(userLogin)) { throw new UsernameNotFoundException("No user found."); }

        return new UserPrincipal(userLogin);
    }
}
