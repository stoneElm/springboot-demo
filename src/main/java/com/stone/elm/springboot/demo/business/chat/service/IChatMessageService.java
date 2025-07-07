package com.stone.elm.springboot.demo.business.chat.service;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatMessageAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatMessageVO;

import java.util.List;

public interface IChatMessageService {

    /**
     * 查询聊天消息表列表Service
     * @param chatMessageAO
     * @return
     */
    ResponseResult<List<ChatMessageVO>> selectChatMessageList(ChatMessageAO chatMessageAO);

    ResponseResult<List<ChatMessageVO>> selectChatMessageListByConversationID(ChatMessageAO chatMessageAO);

    /**
     * 创建聊天消息表列表Service
     * @param chatMessageList
     * @return
     */
    ResponseResult<List<ChatMessageVO>> createChatMessageList(List<ChatMessageAO> chatMessageList);

    /**
     * 更新聊天消息表列表Service
     * @param chatMessageList
     * @return
     */
    ResponseResult<List<ChatMessageVO>> updateChatMessageList(List<ChatMessageAO> chatMessageList);

    /**
     * 删除聊天消息表列表Service
     * @param chatMessageList
     * @return
     */
    ResponseResult<List<ChatMessageVO>> deleteChatMessageList(List<ChatMessageAO> chatMessageList);
}
