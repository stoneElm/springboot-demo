package com.stone.elm.springboot.demo.business.chat.service;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationAO;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationAppAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationVO;

import java.util.List;

public interface IChatConversationService {

    /**
     * 查询聊天会话表列表Service
     * @param chatConversationAO
     * @return
     */
    ResponseResult<List<ChatConversationVO>> selectChatConversationList(ChatConversationAO chatConversationAO);

    /**
     * 创建聊天会话表列表Service
     * @param chatConversationAO
     * @return
     */
    ResponseResult<List<ChatConversationVO>> createChatConversationList(List<ChatConversationAO> createChatConversationList);

    /**
     * 更新聊天会话表列表Service
     * @param chatConversationList
     * @return
     */
    ResponseResult<List<ChatConversationVO>> updateChatConversationList(List<ChatConversationAO> chatConversationList);

    /**
     * 删除聊天会话表列表Service
     * @param chatConversationList
     * @return
     */
    ResponseResult<List<ChatConversationVO>> deleteChatConversationList(List<ChatConversationAO> chatConversationList);

    /**
     * 生成聊天会话表列表Service
     * @param chatConversationAppList
     * @return
     */
    ResponseResult<List<ChatConversationVO>> createChatConversationListByAppList(List<ChatConversationAppAO> chatConversationAppList);

}
