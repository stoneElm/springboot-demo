package com.stone.elm.springboot.demo.business.chat.mapper;

import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationAppAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationAppVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatConversationAppMapper {

    /**
     * 查询聊天会话申请表列表Mapper
     * @param chatConversationAppAO
     * @return
     */
    List<ChatConversationAppVO> selectChatConversationAppList(ChatConversationAppAO chatConversationAppAO);

    /**
     * 统计聊天会话申请表列表Mapper
     * @param chatConversationAppAO
     * @return
     */
    Integer countChatConversationAppAll(ChatConversationAppAO chatConversationAppAO);

    /**
     * 创建聊天会话申请表列表Mapper
     * @param createChatConversationAppList
     * @return
     */
    Integer createChatConversationAppList(@Param("createChatConversationAppList") List<ChatConversationAppAO> createChatConversationAppList);

    /**
     * 修改聊天会话申请表列表Mapper
     * @param updateChatConversationAppList
     * @return
     */
    Integer updateChatConversationAppList(@Param("updateChatConversationAppList") List<ChatConversationAppAO> updateChatConversationAppList);

    /**
     * 删除聊天会话申请表列表Mapper
     * @param deleteChatConversationAppList
     * @return
     */
    Integer deleteChatConversationAppList(@Param("deleteChatConversationAppList") List<ChatConversationAppAO> deleteChatConversationAppList);

}
