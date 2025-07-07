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

}
