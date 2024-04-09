package com.stone.elm.springboot.demo.business.user.model.ao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "LoginInfoAO", description = "用户登录信息实体AO")
public class LoginInfoAO {
    public interface loginGroup{};

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "手机号码")
    private String userPhone;

    @ApiModelProperty(value = "验证码")
    private String verificationCode;

    @ApiModelProperty(value = "登陆模式")
    @NotBlank(message = "登陆模式不能为空", groups = {LoginInfoAO.loginGroup.class})
    private String loginModel;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(String loginModel) {
        this.loginModel = loginModel;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Override
    public String toString() {
        return "LoginInfoAO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", loginModel='" + loginModel + '\'' +
                '}';
    }
}
