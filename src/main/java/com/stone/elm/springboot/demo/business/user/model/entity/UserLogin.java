package com.stone.elm.springboot.demo.business.user.model.entity;

import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserLogin implements UserDetails {
    public UserLogin() {
    }

    public UserLogin(UserInfoVO userInfoVO) {
        this.userInfoVO = userInfoVO;
    }

    private UserInfoVO userInfoVO;

    public UserInfoVO getUserInfoVO() {
        return userInfoVO;
    }

    public void setUserInfoVO(UserInfoVO userInfoVO) {
        this.userInfoVO = userInfoVO;
    }

    /**
     * 返回当前用户所拥有的权限列表
     * @return List<String>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return userInfoVO.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfoVO.getUserName();
    }

    /**
     * 用于检查用户账户是否已经过期
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 检查用户账户是否被锁定
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用于判断用户的凭证（如密码）是否已过期
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否可用
     * @return boolean
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
