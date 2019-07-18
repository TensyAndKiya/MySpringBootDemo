package com.clei.entity.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * security的用户详情类
 * @author kiya
 * @since 2019-07-12 14:08
 */
public class MyUserDetails implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails(String username,String password,Collection<? extends GrantedAuthority> authorities){
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 用户账户是否未过期，过期的账户无法通过身份验证
     * @return true 账户有效
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户账户是否未被锁定，锁定的用户无法进行身份验证
     * @return true 未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户凭据是否未过期 过期会阻止身份验证
     * @return true 未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 指定用户是启用还是禁用，禁用的用户无法进行身份验证
     * @return true 启用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "MyUserDetails{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
