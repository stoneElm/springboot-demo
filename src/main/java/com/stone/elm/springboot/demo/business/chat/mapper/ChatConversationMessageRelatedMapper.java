package com.stone.elm.springboot.demo.business.chat.mapper;

import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationMessageRelatedAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationMessageRelatedVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatConversationMessageRelatedMapper {

    /**
     * 查询聊天会话关联消息表列表Mapper
     * @param chatConversationMessageRelatedAO
     * @return
     */
    List<ChatConversationMessageRelatedVO> selectChatConversationMessageRelatedList(ChatConversationMessageRelatedAO chatConversationMessageRelatedAO);

    /**
     * 统计聊天会话关联消息表列表Mapper
     * @param chatConversationMessageRelatedAO
     * @return
     */
    Integer countChatConversationMessageRelatedAll(ChatConversationMessageRelatedAO chatConversationMessageRelatedAO);

    /**
     * 创建聊天会话关联消息表列表Mapper
     * @param createChatConversationMessageRelatedList
     * @return
     */
    Integer createChatConversationMessageRelatedList(@Param("createChatConversationMessageRelatedList") List<ChatConversationMessageRelatedAO> createChatConversationMessageRelatedList);

    /**
     * 修改聊天会话关联消息表列表Mapper
     * @param updateChatConversationMessageRelatedList
     * @return
     */
    Integer updateChatConversationMessageRelatedList(@Param("updateChatConversationMessageRelatedList") List<ChatConversationMessageRelatedAO> updateChatConversationMessageRelatedList);

    /**
     * 删除聊天会话关联消息表列表Mapper
     * @param deleteChatConversationMessageRelatedList
     * @return
     */
    Integer deleteChatConversationMessageRelatedList(@Param("deleteChatConversationMessageRelatedList") List<ChatConversationMessageRelatedAO> deleteChatConversationMessageRelatedList);

}
