package com.example.CMSR2S.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.CMSR2S.entity.Member;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Tùy chỉnh phần này nếu bạn có các quyền cụ thể cho người dùng
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

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

    // Getter cho đối tượng Member nếu cần
    public Member getMember() {
        return member;
    }
}
