package com.stone.elm.springboot.demo.business.user.controller;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.types.ValidList;
import com.stone.elm.springboot.demo.business.user.model.ao.UserInfoAO;
import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;
import com.stone.elm.springboot.demo.business.user.service.IUserInfoService;
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

@Api(tags = "用户管理/用户信息")
@RestController("userInfoController")
@RequestMapping("/user/userInfo")
public class UserInfoController {
    @Autowired
    private IUserInfoService iUserInfoService;

    @PostMapping("/selectUserInfoList")
    @ApiOperation(value = "查询用户信息表列表 维护人:Lan StoneElm")
    public ResponseResult<List<UserInfoVO>> selectUserInfoList (
            @ApiParam(name = "UserInfoAO", value = "用户信息表实体AO") @RequestBody @Validated(UserInfoAO.selectGroup.class) UserInfoAO userInfoAO) {
        return iUserInfoService.selectUserInfoList(userInfoAO);
    }

    @PostMapping("/createUserInfoList")
    @ApiOperation(value = "新增用户信息表列表 维护人:Lan StoneElm")
    public ResponseResult<List<UserInfoVO>> createUserInfoList (
            @ApiParam(name = "UserInfoAO", value = "用户信息表实体AO") @RequestBody @Validated(UserInfoAO.createGroup.class) ValidList<UserInfoAO> userInfoList) {
        return iUserInfoService.createUserInfoList(userInfoList);
    }

    @PostMapping("/updateUserInfoList")
    @ApiOperation(value = "更新用户信息表列表 维护人:Lan StoneElm")
    public ResponseResult<List<UserInfoVO>> updateUserInfoList (
            @ApiParam(name = "UserInfoAO", value = "用户信息表实体AO") @RequestBody @Validated(UserInfoAO.updateGroup.class) ValidList<UserInfoAO> userInfoList) {
        return iUserInfoService.updateUserInfoList(userInfoList);
    }

    @PostMapping("/deleteUserInfoList")
    @ApiOperation(value = "删除用户信息表列表 维护人:Lan StoneElm")
    public ResponseResult<List<UserInfoVO>> deleteUserInfoList (
            @ApiParam(name = "UserInfoAO", value = "用户信息表实体AO") @RequestBody @Validated(UserInfoAO.deleteGroup.class) ValidList<UserInfoAO> userInfoList) {
        return iUserInfoService.deleteUserInfoList(userInfoList);
    }
}
