package com.stone.elm.springboot.demo.business.chat.service;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationAppAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationAppVO;

import java.util.List;

public interface IChatConversationAppService {

    /**
     * 查询聊天会话申请表列表Service
     * @param chatConversationAppAO
     * @return
     */
    ResponseResult<List<ChatConversationAppVO>> selectChatConversationAppList(ChatConversationAppAO chatConversationAppAO);

    /**
     * 创建聊天会话申请表列表Service
     * @param chatConversationAppList
     * @return
     */
    ResponseResult<List<ChatConversationAppVO>> createChatConversationAppList(List<ChatConversationAppAO> chatConversationAppList);

    /**
     * 更新聊天会话申请表列表Service
     * @param chatConversationAppList
     * @return
     */
    ResponseResult<List<ChatConversationAppVO>> updateChatConversationAppList(List<ChatConversationAppAO> chatConversationAppList);

    /**
     * 删除聊天会话申请表列表Service
     * @param chatConversationAppList
     * @return
     */
    ResponseResult<List<ChatConversationAppVO>> deleteChatConversationAppList(List<ChatConversationAppAO> chatConversationAppList);

}
