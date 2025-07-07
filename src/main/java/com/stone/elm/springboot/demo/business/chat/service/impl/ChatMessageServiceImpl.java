package com.stone.elm.springboot.demo.business.chat.service.impl;

import com.stone.elm.springboot.demo.basictech.common.constant.CodeClsConstant;
import com.stone.elm.springboot.demo.basictech.common.exception.BusinessException;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseConstant;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.response.ResultUtils;
import com.stone.elm.springboot.demo.basictech.common.service.IPrimaryKeyService;
import com.stone.elm.springboot.demo.basictech.common.utils.AuthenticationUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.BeanCopyUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.DateUtils;
import com.stone.elm.springboot.demo.basictech.common.utils.JsonUtil;
import com.stone.elm.springboot.demo.business.chat.mapper.ChatConversationMapper;
import com.stone.elm.springboot.demo.business.chat.mapper.ChatConversationMessageRelatedMapper;
import com.stone.elm.springboot.demo.business.chat.mapper.ChatMessageMapper;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationAO;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationMessageRelatedAO;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatMessageAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationVO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatMessageVO;
import com.stone.elm.springboot.demo.business.chat.service.IChatMessageService;
import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements IChatMessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatMessageServiceImpl.class);

    @Autowired
    private IPrimaryKeyService iPrimaryKeyService;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private ChatConversationMapper chatConversationMapper;

    @Autowired
    private ChatConversationMessageRelatedMapper chatConversationMessageRelatedMapper;

    /**
     * 查询聊天消息表列表ServiceImpl
     * @param chatMessageAO
     * @return
     */
    @Override
    public ResponseResult<List<ChatMessageVO>> selectChatMessageList(ChatMessageAO chatMessageAO) {
        LOGGER.info("查询聊天消息表入参:{}", JsonUtil.convertObjectToJson(chatMessageAO));

        List<ChatMessageVO> resultData = chatMessageMapper.selectChatMessageList(chatMessageAO);
        LOGGER.info("根据条件查询聊天消息表信息列表出参:{}", JsonUtil.convertObjectToJson(resultData));

        Integer countChatMessageAll = chatMessageMapper.countChatMessageAll(chatMessageAO);
        LOGGER.info("根据条件统计结果:{}", countChatMessageAll);

        ResponseResult<List<ChatMessageVO>> result = ResultUtils.wrapResult(resultData);
        result.setTotal(countChatMessageAll);

        LOGGER.info("查询聊天消息表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    @Override
    public ResponseResult<List<ChatMessageVO>> selectChatMessageListByConversationID(ChatMessageAO chatMessageAO) {
        LOGGER.info("根据会话标识查询聊天消息表入参:{}", JsonUtil.convertObjectToJson(chatMessageAO));

        List<ChatMessageVO> resultData = chatMessageMapper.selectChatMessageListByConversationID(chatMessageAO);
        LOGGER.info("根据条件查询聊天消息表信息列表出参:{}", JsonUtil.convertObjectToJson(resultData));

        // 获取当前登陆人信息
        UserInfoVO userInfo = AuthenticationUtil.getUserAndRoleInfo();

        for (ChatMessageVO datum : resultData) {
            // 处理时间格式
            if (StringUtils.isNotBlank(datum.getMessageSendDate())) {
                datum.setMessageSendDateStr(DateUtils.getReadTimeFormat(datum.getMessageSendDate()));
            }

            // 是否当前用户的消息
            datum.setCurrentUserMessage(userInfo.getUserID() == datum.getSenderID());
        }

        ResponseResult<List<ChatMessageVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("根据会话标识查询聊天消息表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 创建聊天消息表列表ServiceImpl
     * @param createChatMessageList
     * @return
     */
    @Override
    public ResponseResult<List<ChatMessageVO>> createChatMessageList(List<ChatMessageAO> createChatMessageList) {
        LOGGER.info("创建聊天消息表入参:{}", JsonUtil.convertObjectToJson(createChatMessageList));

        if (CollectionUtils.isEmpty(createChatMessageList)) {
            return ResultUtils.wrapResult();
        }

        // 获取当前系统时间
        String currentFormat = DateUtils.getCurrentFormat();

        // 获取当前登陆人信息
        UserInfoVO userInfo = AuthenticationUtil.getUserAndRoleInfo();

        ArrayList<ChatConversationMessageRelatedAO> createRelatedList = new ArrayList<>();

        for (ChatMessageAO chatMessageAO : createChatMessageList) {
            chatMessageAO.setChatMessageID(iPrimaryKeyService.getPrimaryKey());
            chatMessageAO.setMessageSendDate(currentFormat);

            ChatConversationAO selectParam = new ChatConversationAO();
            selectParam.setChatConversationNo(chatMessageAO.getChatConversationNo());
            List<ChatConversationVO> chatConversationList = chatConversationMapper.selectChatConversationList(selectParam);

            if (CollectionUtils.isEmpty(chatConversationList)) {
                throw new BusinessException("无法找到对应的会话消息！", ResponseConstant.FAIL);
            }

            for (ChatConversationVO chatConversation : chatConversationList) {
                ChatConversationMessageRelatedAO chatConversationMessageRelatedAO = new ChatConversationMessageRelatedAO();
                chatConversationMessageRelatedAO.setChatConversationMessageRelatedID(iPrimaryKeyService.getPrimaryKey());
                chatConversationMessageRelatedAO.setChatConversationID(chatConversation.getChatConversationID());
                chatConversationMessageRelatedAO.setChatMessageID(chatMessageAO.getChatMessageID());
                if (userInfo.getUserID() == chatConversation.getChatConversationActorID()) {
                    chatConversationMessageRelatedAO.setIsRead(CodeClsConstant.IS_FLAG_YES);
                    chatConversationMessageRelatedAO.setReadDate(currentFormat);
                } else {
                    chatConversationMessageRelatedAO.setIsRead(CodeClsConstant.IS_FLAG_NO);
                }

                createRelatedList.add(chatConversationMessageRelatedAO);
            }
        }

        Integer row = chatMessageMapper.createChatMessageList(createChatMessageList);
        LOGGER.info("成功执行{}条数据", row);

        Integer insertRelatedRow = chatConversationMessageRelatedMapper.createChatConversationMessageRelatedList(createRelatedList);
        LOGGER.info("成功执行{}条数据", row);

        List<ChatMessageVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(createChatMessageList, resultData, ChatMessageVO.class);

        ResponseResult<List<ChatMessageVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("创建聊天消息表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 更新聊天消息表列表ServiceImpl
     * @param updateChatMessageList
     * @return
     */
    @Override
    public ResponseResult<List<ChatMessageVO>> updateChatMessageList(List<ChatMessageAO> updateChatMessageList) {
        LOGGER.info("更新聊天消息表入参:{}", JsonUtil.convertObjectToJson(updateChatMessageList));

        if (CollectionUtils.isEmpty(updateChatMessageList)) {
            return ResultUtils.wrapResult();
        }

        Integer row = chatMessageMapper.updateChatMessageList(updateChatMessageList);
        LOGGER.info("成功执行{}条数据", row);

        List<ChatMessageVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(updateChatMessageList, resultData, ChatMessageVO.class);

        ResponseResult<List<ChatMessageVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("更新聊天消息表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 删除聊天消息表列表ServiceImpl
     * @param deleteChatMessageList
     * @return
     */
    @Override
    public ResponseResult<List<ChatMessageVO>> deleteChatMessageList(List<ChatMessageAO> deleteChatMessageList) {
        LOGGER.info("删除聊天消息表入参:{}", JsonUtil.convertObjectToJson(deleteChatMessageList));

        if (CollectionUtils.isEmpty(deleteChatMessageList)) {
            return ResultUtils.wrapResult();
        }

        Integer row = chatMessageMapper.deleteChatMessageList(deleteChatMessageList);
        LOGGER.info("成功执行{}条数据", row);

        List<ChatMessageVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(deleteChatMessageList, resultData, ChatMessageVO.class);

        ResponseResult<List<ChatMessageVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("删除聊天消息表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

}
