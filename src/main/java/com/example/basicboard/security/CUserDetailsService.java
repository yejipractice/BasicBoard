package com.example.basicboard.security;

import com.example.basicboard.advice.exception.CUserNotFoundException;
import com.example.basicboard.models.User;
import com.example.basicboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String userPk) {
        try{
            User user = (User)userRepository.findByUserId(userPk).orElseThrow(CUserNotFoundException::new);
            return user;
        }catch (Throwable t){
            t.printStackTrace();
        }
        return null;
    }
}
