package com.stone.elm.springboot.demo.business.user.model.ao;

import com.stone.elm.springboot.demo.basictech.common.requset.QueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.List;

@ApiModel(value = "UserInfoAO", description = "用户信息实体AO")
public class UserInfoAO extends QueryEntity {
    public interface registerGroup{};

    @ApiModelProperty(value = "用户唯一标识")
    private Long userID;

    private List<Long> userIDList;

    @ApiModelProperty(value = "用户名称")
    @NotBlank(message = "用户名称不能为空", groups = {UserInfoAO.registerGroup.class})
    private String userName;

    @ApiModelProperty(value = "用户手机号")
    @NotBlank(message = "手机号吗不能为空", groups = {UserInfoAO.registerGroup.class})
    private String userPhone;

    @ApiModelProperty(value = "用户昵称")
    @NotBlank(message = "用户昵称不能为空", groups = {UserInfoAO.registerGroup.class})
    private String nickName;

    @ApiModelProperty(value = "用户密码")
    @NotBlank(message = "用户密码不能为空", groups = {UserInfoAO.registerGroup.class})
    private String password;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getUserIDList() {
        return userIDList;
    }

    public void setUserIDList(List<Long> userIDList) {
        this.userIDList = userIDList;
    }

    @Override
    public String toString() {
        return "UserInfoAO{" +
                "userID=" + userID +
                ", userIDList=" + userIDList +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
