package com.stone.elm.springboot.demo.business.user.service.impl;

import com.stone.elm.springboot.demo.basictech.common.exception.BusinessException;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseConstant;
import com.stone.elm.springboot.demo.business.user.mapper.UserInfoMapper;
import com.stone.elm.springboot.demo.business.user.model.ao.UserInfoAO;
import com.stone.elm.springboot.demo.business.user.model.entity.UserLogin;
import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        UserInfoAO userInfoAO = new UserInfoAO();
        userInfoAO.setUserName(username);
        List<UserInfoVO> userInfoList = userInfoMapper.selectUserInfoList(userInfoAO);

        //如果没有查询到用户，就抛出异常
        if (CollectionUtils.isEmpty(userInfoList)) {
            throw new BusinessException("用户名或者密码错误！", ResponseConstant.FAIL);
        }

        if (userInfoList.size() > 1) {
            throw new BusinessException("当前用户数据有误,请联系管理员！", ResponseConstant.FAIL);
        }

        //将数据封装成UserDetails
        UserInfoVO result = userInfoList.stream().findFirst().get();
        return new UserLogin(result);
    }
}
