package com.stone.elm.springboot.demo.business.chat.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ChatMessageVO", description = "聊天消息表查询VO")
public class ChatMessageVO {

    @ApiModelProperty(value = "聊天消息表唯一标识")
    private Long chatMessageID;

    @ApiModelProperty(value = "发送者唯一标识")
    private Long senderID;

    @ApiModelProperty(value = "接收者唯一标识")
    private Long receiverID;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "消息类型（01：文字、02：语音、03：视频、04：文件）")
    private String messageType;

    @ApiModelProperty(value = "消息发送时间")
    private String messageSendDate;

    @ApiModelProperty(value = "消息发送时间format")
    private String messageSendDateStr;

    @ApiModelProperty(value = "是否当前用户的消息")
    private Boolean isCurrentUserMessage;

    @ApiModelProperty(value = "会话对象头像附件标识")
    private Long avatarAttachDtlID;

    public Long getChatMessageID() {
        return chatMessageID;
    }

    public void setChatMessageID(Long chatMessageID) {
        this.chatMessageID = chatMessageID;
    }

    public Long getSenderID() {
        return senderID;
    }

    public void setSenderID(Long senderID) {
        this.senderID = senderID;
    }

    public Long getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(Long receiverID) {
        this.receiverID = receiverID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageSendDate() {
        return messageSendDate;
    }

    public void setMessageSendDate(String messageSendDate) {
        this.messageSendDate = messageSendDate;
    }

    public String getMessageSendDateStr() {
        return messageSendDateStr;
    }

    public void setMessageSendDateStr(String messageSendDateStr) {
        this.messageSendDateStr = messageSendDateStr;
    }

    public Boolean getCurrentUserMessage() {
        return isCurrentUserMessage;
    }

    public void setCurrentUserMessage(Boolean currentUserMessage) {
        isCurrentUserMessage = currentUserMessage;
    }

    public Long getAvatarAttachDtlID() {
        return avatarAttachDtlID;
    }

    public void setAvatarAttachDtlID(Long avatarAttachDtlID) {
        this.avatarAttachDtlID = avatarAttachDtlID;
    }
}
