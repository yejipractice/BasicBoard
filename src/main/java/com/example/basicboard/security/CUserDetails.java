package com.example.basicboard.security;

import com.example.basicboard.models.User;
import com.example.basicboard.models.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CUserDetails implements UserDetails {
    private final User user;
    private static final String ROLE_PREFIX = "ROLE_";

    public CUserDetails(User user){
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole userRole = user.getRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ROLE_PREFIX+userRole.toString());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 권한이 여러 개일 수도 있으므로
        authorities.add(authority);
        return  authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() { // 계정이 만료 안되었는지
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정이 잠기지 않았는지
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 계정 패스워드가 만료 안되었는지
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정 사용 가능한지
        return true;
    }
}
