package com.stone.elm.springboot.demo.business.chat.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ChatConversationMessageRelatedVO", description = "聊天会话关联消息表查询VO")
public class ChatConversationMessageRelatedVO {

    @ApiModelProperty(value = "聊天会话关联消息表唯一标识")
    private Long chatConversationMessageRelatedID;

    @ApiModelProperty(value = "关联聊天会话唯一标识")
    private Long chatConversationID;

    @ApiModelProperty(value = "关联消息唯一标识")
    private Long chatMessageID;

    @ApiModelProperty(value = "是否已读")
    private String isRead;

    @ApiModelProperty(value = "读取消息时间")
    private String readDate;

    public Long getChatConversationMessageRelatedID() {
        return chatConversationMessageRelatedID;
    }

    public void setChatConversationMessageRelatedID(Long chatConversationMessageRelatedID) {
        this.chatConversationMessageRelatedID = chatConversationMessageRelatedID;
    }

    public Long getChatConversationID() {
        return chatConversationID;
    }

    public void setChatConversationID(Long chatConversationID) {
        this.chatConversationID = chatConversationID;
    }

    public Long getChatMessageID() {
        return chatMessageID;
    }

    public void setChatMessageID(Long chatMessageID) {
        this.chatMessageID = chatMessageID;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getReadDate() {
        return readDate;
    }

    public void setReadDate(String readDate) {
        this.readDate = readDate;
    }
}
