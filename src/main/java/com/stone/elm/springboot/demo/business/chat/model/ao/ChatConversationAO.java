package com.stone.elm.springboot.demo.business.chat.model.ao;

import com.stone.elm.springboot.demo.basictech.common.requset.QueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(value = "ChatConversationAO", description = "聊天会话表查询AO")
public class ChatConversationAO extends QueryEntity {
    public interface selectGroup{}
    public interface createGroup{}
    public interface updateGroup{}
    public interface deleteGroup{}

    @NotNull(message = "聊天会话唯一标识不能为空", groups = {updateGroup.class, deleteGroup.class})
    @ApiModelProperty(value = "聊天会话唯一标识")
    private Long chatConversationID;

    @ApiModelProperty(value = "聊天会话（房间号）编码")
    private String chatConversationNo;

    @ApiModelProperty(value = "聊天会话名称")
    private String chatConversationName;

    @ApiModelProperty(value = "会话类型：01（个人）、02（组）")
    private String chatConversationType;

    @ApiModelProperty(value = "会话参与者")
    private String chatConversationActor;

    @ApiModelProperty(value = "加入时间")
    private String joinTime;

    @ApiModelProperty(value = "添加对象名称")
    private String addedUserName;

    @ApiModelProperty(value = "添加对象唯一标志")
    private String addUserID;

    @ApiModelProperty(value = "添加对象唯一标志")
    private String userID;

    public Long getChatConversationID() {
        return chatConversationID;
    }

    public void setChatConversationID(Long chatConversationID) {
        this.chatConversationID = chatConversationID;
    }

    public String getChatConversationNo() {
        return chatConversationNo;
    }

    public void setChatConversationNo(String chatConversationNo) {
        this.chatConversationNo = chatConversationNo;
    }

    public String getChatConversationName() {
        return chatConversationName;
    }

    public void setChatConversationName(String chatConversationName) {
        this.chatConversationName = chatConversationName;
    }

    public String getChatConversationType() {
        return chatConversationType;
    }

    public void setChatConversationType(String chatConversationType) {
        this.chatConversationType = chatConversationType;
    }

    public String getChatConversationActor() {
        return chatConversationActor;
    }

    public void setChatConversationActor(String chatConversationActor) {
        this.chatConversationActor = chatConversationActor;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getAddedUserName() {
        return addedUserName;
    }

    public void setAddedUserName(String addedUserName) {
        this.addedUserName = addedUserName;
    }

    public String getAddUserID() {
        return addUserID;
    }

    public void setAddUserID(String addUserID) {
        this.addUserID = addUserID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
