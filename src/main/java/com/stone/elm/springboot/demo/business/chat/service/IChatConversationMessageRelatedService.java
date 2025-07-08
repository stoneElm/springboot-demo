package com.stone.elm.springboot.demo.business.chat.service;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.types.ValidList;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationMessageRelatedAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationMessageRelatedVO;

import java.util.List;

public interface IChatConversationMessageRelatedService {

    /**
     * 查询聊天会话关联消息表列表Service
     * @param chatConversationMessageRelatedAO
     * @return
     */
    ResponseResult<List<ChatConversationMessageRelatedVO>> selectChatConversationMessageRelatedList(ChatConversationMessageRelatedAO chatConversationMessageRelatedAO);

    /**
     * 创建聊天会话关联消息表列表Service
     * @param chatConversationMessageRelatedList
     * @return
     */
    ResponseResult<List<ChatConversationMessageRelatedVO>> createChatConversationMessageRelatedList(List<ChatConversationMessageRelatedAO> chatConversationMessageRelatedList);

    /**
     * 更新聊天会话关联消息表列表Service
     * @param chatConversationMessageRelatedList
     * @return
     */
    ResponseResult<List<ChatConversationMessageRelatedVO>> updateChatConversationMessageRelatedList(List<ChatConversationMessageRelatedAO> chatConversationMessageRelatedList);

    ResponseResult<List<ChatConversationMessageRelatedVO>> markReadMessageForCurrentLogin(ValidList<ChatConversationMessageRelatedAO> chatConversationMessageRelatedList);

    /**
     * 删除聊天会话关联消息表列表Service
     * @param chatConversationMessageRelatedList
     * @return
     */
    ResponseResult<List<ChatConversationMessageRelatedVO>> deleteChatConversationMessageRelatedList(List<ChatConversationMessageRelatedAO> chatConversationMessageRelatedList);
}
