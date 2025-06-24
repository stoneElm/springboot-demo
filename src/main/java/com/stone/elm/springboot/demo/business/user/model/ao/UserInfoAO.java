package com.stone.elm.springboot.demo.business.user.model.ao;

import com.stone.elm.springboot.demo.basictech.common.requset.QueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "UserInfoAO", description = "用户信息实体AO")
public class UserInfoAO extends QueryEntity {
    public interface registerGroup{};

    public interface selectGroup{}
    public interface createGroup{}
    public interface updateGroup{}
    public interface deleteGroup{}

    @NotNull(message = "用户信息唯一标识不能为空", groups = {updateGroup.class, deleteGroup.class})
    @ApiModelProperty(value = "用户信息唯一标识")
    private Long userID;

    @ApiModelProperty(value = "在线状态")
    private String onlineStat;

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

    public String getOnlineStat() {
        return onlineStat;
    }

    public void setOnlineStat(String onlineStat) {
        this.onlineStat = onlineStat;
    }
}
