package com.stone.elm.springboot.demo.business.chat.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ChatConversationVO", description = "聊天会话表查询VO")
public class ChatConversationVO {

    @ApiModelProperty(value = "聊天会话唯一标识")
    private Long chatConversationID;

    @ApiModelProperty(value = "聊天会话（房间号）编码")
    private Long chatConversationNo;

    @ApiModelProperty(value = "聊天会话名称")
    private String chatConversationName;

    @ApiModelProperty(value = "会话类型：01（个人）、02（组）")
    private String chatConversationType;

    @ApiModelProperty(value = "会话参与者")
    private Long chatConversationActorID;

    @ApiModelProperty(value = "加入时间")
    private String joinTime;

    @ApiModelProperty(value = "会话对象唯一标识")
    private Long conversationObjectID;

    @ApiModelProperty(value = "会话对象名称")
    private String conversationUserName;

    @ApiModelProperty(value = "会话对象昵称")
    private String conversationNickName;

    @ApiModelProperty(value = "会话对象昵称")
    private String conversationOnLineStat;

    @ApiModelProperty(value = "会话对象昵称")
    private Integer unreadMessagesNumber;

    @ApiModelProperty(value = "会话最后消息")
    private String conversationLastMessage;

    @ApiModelProperty(value = "头像附件唯一标识")
    private Long avatarAttachDtlID;

    @ApiModelProperty(value = "会话最后消息时间")
    private String conversationLastMessageDate;

    public Long getChatConversationID() {
        return chatConversationID;
    }

    public void setChatConversationID(Long chatConversationID) {
        this.chatConversationID = chatConversationID;
    }

    public Long getChatConversationNo() {
        return chatConversationNo;
    }

    public void setChatConversationNo(Long chatConversationNo) {
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

    public Long getChatConversationActorID() {
        return chatConversationActorID;
    }

    public void setChatConversationActorID(Long chatConversationActorID) {
        this.chatConversationActorID = chatConversationActorID;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public Long getConversationObjectID() {
        return conversationObjectID;
    }

    public void setConversationObjectID(Long conversationObjectID) {
        this.conversationObjectID = conversationObjectID;
    }

    public String getConversationUserName() {
        return conversationUserName;
    }

    public void setConversationUserName(String conversationUserName) {
        this.conversationUserName = conversationUserName;
    }

    public String getConversationNickName() {
        return conversationNickName;
    }

    public void setConversationNickName(String conversationNickName) {
        this.conversationNickName = conversationNickName;
    }

    public String getConversationOnLineStat() {
        return conversationOnLineStat;
    }

    public void setConversationOnLineStat(String conversationOnLineStat) {
        this.conversationOnLineStat = conversationOnLineStat;
    }

    public Integer getUnreadMessagesNumber() {
        return unreadMessagesNumber;
    }

    public void setUnreadMessagesNumber(Integer unreadMessagesNumber) {
        this.unreadMessagesNumber = unreadMessagesNumber;
    }

    public String getConversationLastMessage() {
        return conversationLastMessage;
    }

    public void setConversationLastMessage(String conversationLastMessage) {
        this.conversationLastMessage = conversationLastMessage;
    }

    public Long getAvatarAttachDtlID() {
        return avatarAttachDtlID;
    }

    public void setAvatarAttachDtlID(Long avatarAttachDtlID) {
        this.avatarAttachDtlID = avatarAttachDtlID;
    }

    public String getConversationLastMessageDate() {
        return conversationLastMessageDate;
    }

    public void setConversationLastMessageDate(String conversationLastMessageDate) {
        this.conversationLastMessageDate = conversationLastMessageDate;
    }
}
