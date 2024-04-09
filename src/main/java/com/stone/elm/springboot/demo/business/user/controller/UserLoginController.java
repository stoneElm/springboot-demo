package com.stone.elm.springboot.demo.business.user.controller;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.business.user.model.ao.LoginInfoAO;
import com.stone.elm.springboot.demo.business.user.model.ao.UserInfoAO;
import com.stone.elm.springboot.demo.business.user.model.vo.LoginInfoVO;
import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;
import com.stone.elm.springboot.demo.business.user.service.IUserLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "用户管理/用户登录")
@RestController("userLoginController")
@RequestMapping("/user/userLogin")
public class UserLoginController {
    @Autowired
    private IUserLoginService iUserLoginService;

    @PostMapping("/login")
    @ApiOperation(value = "登录操作 维护人:Lan StoneElm")
    public ResponseResult<LoginInfoVO> login (
            @ApiParam(name = "LoginInfoAO", value = "用户登录信息实体AO") @RequestBody @Validated(LoginInfoAO.loginGroup.class) LoginInfoAO loginInfoAO) {
        return iUserLoginService.login(loginInfoAO);
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册操作 维护人:Lan StoneElm")
    public ResponseResult<List<UserInfoVO>> register (
            @ApiParam(name = "UserInfoVO", value = "用户注册信息实体AO") @RequestBody @Validated(UserInfoAO.registerGroup.class) UserInfoAO userInfoAO) {
        return iUserLoginService.register(userInfoAO);
    }
}
