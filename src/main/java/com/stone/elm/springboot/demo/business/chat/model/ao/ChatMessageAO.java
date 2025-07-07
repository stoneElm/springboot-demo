package com.stone.elm.springboot.demo.business.chat.model.ao;

import com.stone.elm.springboot.demo.basictech.common.requset.QueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(value = "ChatMessageAO", description = "聊天消息表查询AO")
public class ChatMessageAO extends QueryEntity {
    public interface selectGroup{}
    public interface selectBYCIDGroup{}
    public interface createGroup{}
    public interface updateGroup{}
    public interface deleteGroup{}

    @NotNull(message = "聊天消息表唯一标识不能为空", groups = {updateGroup.class, deleteGroup.class})
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

    @ApiModelProperty(value = "")
    private String messageSendDate;

    @NotNull(message = "会话编号不能为空", groups = {createGroup.class})
    @ApiModelProperty(value = "会话编号")
    private Long chatConversationNo;

    @NotNull(message = "会话编号不能为空", groups = {selectBYCIDGroup.class})
    @ApiModelProperty(value = "聊天会话唯一标识")
    private Long chatConversationID;

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

    public Long getChatConversationNo() {
        return chatConversationNo;
    }

    public void setChatConversationNo(Long chatConversationNo) {
        this.chatConversationNo = chatConversationNo;
    }

    public Long getChatConversationID() {
        return chatConversationID;
    }

    public void setChatConversationID(Long chatConversationID) {
        this.chatConversationID = chatConversationID;
    }
}