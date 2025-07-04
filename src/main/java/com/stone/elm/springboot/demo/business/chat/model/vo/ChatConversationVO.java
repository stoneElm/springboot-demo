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
}
