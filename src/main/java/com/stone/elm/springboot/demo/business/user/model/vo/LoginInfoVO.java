package com.stone.elm.springboot.demo.business.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "LoginInfoVO", description = "用户登录信息实体VO")
public class LoginInfoVO {

    @ApiModelProperty(value = "token值")
    private String token;

    @ApiModelProperty(value = "文件token值")
    private String fileToken;

    @ApiModelProperty(value = "用户基础信息")
    private UserInfoVO userInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFileToken() {
        return fileToken;
    }

    public void setFileToken(String fileToken) {
        this.fileToken = fileToken;
    }

    public UserInfoVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoVO userInfo) {
        this.userInfo = userInfo;
    }
}
