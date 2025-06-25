package com.stone.elm.springboot.demo.business.chat.mapper;

import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationAO;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationAppAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationAppVO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationVO;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatConversationMapper {

    /**
     * 查询聊天会话表列表Mapper
     * @param chatConversationAO
     * @return
     */
    List<ChatConversationVO> selectChatConversationList(ChatConversationAO chatConversationAO);

    List<ChatConversationVO> selectChatConversationListByUserOneAndUserTwo(ChatConversationAO chatConversationAppAO);

    /**
     * 统计聊天会话表列表Mapper
     * @param chatConversationAO
     * @return
     */
    Integer countChatConversationAll(ChatConversationAO chatConversationAO);

    /**
     * 创建聊天会话表列表Mapper
     * @param createChatConversationList
     * @return
     */
    Integer createChatConversationList(@Param("createChatConversationList") List<ChatConversationAO> createChatConversationList);

    /**
     * 修改聊天会话表列表Mapper
     * @param updateChatConversationList
     * @return
     */
    Integer updateChatConversationList(@Param("updateChatConversationList") List<ChatConversationAO> updateChatConversationList);

    /**
     * 删除聊天会话表列表Mapper
     * @param deleteChatConversationList
     * @return
     */
    Integer deleteChatConversationList(@Param("deleteChatConversationList") List<ChatConversationAO> deleteChatConversationList);

}