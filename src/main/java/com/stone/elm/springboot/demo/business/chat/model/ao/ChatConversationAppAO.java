package com.stone.elm.springboot.demo.business.chat.model.ao;

import com.stone.elm.springboot.demo.basictech.common.requset.QueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(value = "ChatConversationAppAO", description = "聊天会话申请表查询AO")
public class ChatConversationAppAO extends QueryEntity {
    public interface selectGroup{}
    public interface createGroup{}
    public interface updateGroup{}
    public interface deleteGroup{}

    @NotNull(message = "聊天会话申请表唯一标识不能为空", groups = {updateGroup.class, deleteGroup.class})
    @ApiModelProperty(value = "聊天会话申请表唯一标识")
    private Long chatConversationAppID;

    @ApiModelProperty(value = "邀请对象唯一标识")
    private Long invitedObjectID;

    @ApiModelProperty(value = "被邀请对象唯一标识")
    private Long beInvitedObjectID;

    @NotNull(message = "聊天会话申请表唯一标识不能为空", groups = {createGroup.class})
    @ApiModelProperty(value = "被邀请对象名称")
    private String beInvitedObjectName;

    @ApiModelProperty(value = "邀请时间")
    private String invitationTime;

    @ApiModelProperty(value = "邀请状态：01（待同意）、02（已同意）、03（被拒绝）")
    private String invitationStatus;

    @ApiModelProperty(value = "验证消息")
    private String verificationMessage;

    public Long getChatConversationAppID() {
        return chatConversationAppID;
    }

    public void setChatConversationAppID(Long chatConversationAppID) {
        this.chatConversationAppID = chatConversationAppID;
    }

    public Long getInvitedObjectID() {
        return invitedObjectID;
    }

    public void setInvitedObjectID(Long invitedObjectID) {
        this.invitedObjectID = invitedObjectID;
    }

    public Long getBeInvitedObjectID() {
        return beInvitedObjectID;
    }

    public void setBeInvitedObjectID(Long beInvitedObjectID) {
        this.beInvitedObjectID = beInvitedObjectID;
    }

    public String getInvitationTime() {
        return invitationTime;
    }

    public void setInvitationTime(String invitationTime) {
        this.invitationTime = invitationTime;
    }

    public String getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public String getVerificationMessage() {
        return verificationMessage;
    }

    public void setVerificationMessage(String verificationMessage) {
        this.verificationMessage = verificationMessage;
    }

    public String getBeInvitedObjectName() {
        return beInvitedObjectName;
    }

    public void setBeInvitedObjectName(String beInvitedObjectName) {
        this.beInvitedObjectName = beInvitedObjectName;
    }

}
