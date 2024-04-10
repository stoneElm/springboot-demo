package com.stone.elm.springboot.demo.business.user.service;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.business.user.model.ao.LoginInfoAO;
import com.stone.elm.springboot.demo.business.user.model.ao.UserInfoAO;
import com.stone.elm.springboot.demo.business.user.model.vo.LoginInfoVO;
import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;

import java.util.List;

public interface IUserLoginService {

    ResponseResult<LoginInfoVO> login(LoginInfoAO loginInfoAO);

    ResponseResult<List<UserInfoVO>> register(UserInfoAO userInfoAO);

    ResponseResult<Object> logout(UserInfoAO userInfoAO);
}
