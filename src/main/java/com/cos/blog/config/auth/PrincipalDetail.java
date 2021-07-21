package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDatails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션에 저장해준다.

public class PrincipalDetail implements UserDetails {

    private User user; //콤포지션

    public PrincipalDetail(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료가되지 않았는지 (true: 만료되지 않음)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있는지 리턴함 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정 활성화 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }
    // 계정이 가지고 있는 권한 목록을 리턴한다. (원래는 여러가지 권한으로 루프를 돌아야하지만 연습용으로 하나의 권한만)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(()->{return "ROLE_" + user.getRole();});
        return collectors;
    }
}
