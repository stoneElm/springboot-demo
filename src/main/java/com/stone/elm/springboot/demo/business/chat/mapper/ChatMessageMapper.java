package com.stone.elm.springboot.demo.business.chat.mapper;

import com.stone.elm.springboot.demo.business.chat.model.ao.ChatMessageAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatMessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatMessageMapper {

    /**
     * 查询聊天消息表列表Mapper
     * @param chatMessageAO
     * @return
     */
    List<ChatMessageVO> selectChatMessageList(ChatMessageAO chatMessageAO);

    List<ChatMessageVO> selectChatMessageListByConversationID(ChatMessageAO chatMessageAO);

    /**
     * 统计聊天消息表列表Mapper
     * @param chatMessageAO
     * @return
     */
    Integer countChatMessageAll(ChatMessageAO chatMessageAO);

    /**
     * 创建聊天消息表列表Mapper
     * @param createChatMessageList
     * @return
     */
    Integer createChatMessageList(@Param("createChatMessageList") List<ChatMessageAO> createChatMessageList);

    /**
     * 修改聊天消息表列表Mapper
     * @param updateChatMessageList
     * @return
     */
    Integer updateChatMessageList(@Param("updateChatMessageList") List<ChatMessageAO> updateChatMessageList);

    /**
     * 删除聊天消息表列表Mapper
     * @param deleteChatMessageList
     * @return
     */
    Integer deleteChatMessageList(@Param("deleteChatMessageList") List<ChatMessageAO> deleteChatMessageList);
}