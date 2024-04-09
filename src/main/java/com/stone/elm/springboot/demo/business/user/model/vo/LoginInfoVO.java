package com.stone.elm.springboot.demo.business.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "LoginInfoVO", description = "用户登录信息实体VO")
public class LoginInfoVO {

    @ApiModelProperty(value = "token值")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginInfoVO{" +
                "token='" + token + '\'' +
                '}';
    }
}
