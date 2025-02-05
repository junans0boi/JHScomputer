package com.example.jhscomputer.users.service;

import com.example.jhscomputer.users.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserSecurityService implements UserDetails {
    private final User user;
    private final List<GrantedAuthority> authorities;

    /* 생성자에서 User 엔티티를 받아와 authorities 리스트를 구성합니다. */
    public UserSecurityService(User user) {
        this.user = user;
        // User 엔티티의 roles(Set<String>)을 사용해 GrantedAuthority 리스트 생성
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    /* 사용자의 권한 목록을 반환합니다. */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /* 사용자 비밀번호를 반환합니다. */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /* 사용자 이름(로그인 ID)을 반환합니다.*/
    @Override
    public String getUsername() {
        return user.getEmail();
    }

     /* 사용자 닉네임(혹은 실제 이름)을 따로 사용하고 싶다면 제공하는 메서드입니다.*/
    public String getDisplayName() {
        return user.getUsername();
    }

    /* 계정 만료 여부(활성화)를 확인하는 메서드들, 여기서는 모두 true로 설정해 로그인에 제한이 없게 합니다. */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}