package com.example.basicboard.security;

import com.example.basicboard.advice.exception.CUserNotFoundException;
import com.example.basicboard.models.User;
import com.example.basicboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username).orElseThrow(CUserNotFoundException::new);
        return new CUserDetails(user);
    }
}
