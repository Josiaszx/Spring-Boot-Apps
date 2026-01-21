package com.app.veterinaria.security;

import com.app.veterinaria.entity.User;
import com.app.veterinaria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user;

        if (username.contains("@")) {
            user = userRepository.findByEmail(username).orElse(null);
        } else {
            user = userRepository.findByUsername(username).orElse(null);
        }

        if (user == null) throw new UsernameNotFoundException("Bad credentials");


        return new UserDetailsImpl(user);
    }
}
