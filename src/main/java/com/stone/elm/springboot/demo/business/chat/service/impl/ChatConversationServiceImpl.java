package com.stone.elm.springboot.demo.business.chat.service.impl;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.response.ResultUtils;
import com.stone.elm.springboot.demo.basictech.common.service.IPrimaryKeyService;
import com.stone.elm.springboot.demo.basictech.common.utils.AuthenticationUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.BeanCopyUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.DateUtils;
import com.stone.elm.springboot.demo.basictech.common.utils.JsonUtil;
import com.stone.elm.springboot.demo.business.chat.mapper.ChatConversationAppMapper;
import com.stone.elm.springboot.demo.business.chat.mapper.ChatConversationMapper;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationAO;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationAppAO;
import com.stone.elm.springboot.demo.business.chat.model.enums.ChatInvitationEnum;
import com.stone.elm.springboot.demo.business.chat.model.enums.ConversationTypeEnum;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationVO;
import com.stone.elm.springboot.demo.business.chat.service.IChatConversationService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatConversationServiceImpl implements IChatConversationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatConversationServiceImpl.class);

    @Autowired
    private IPrimaryKeyService iPrimaryKeyService;

    @Autowired
    private ChatConversationMapper chatConversationMapper;

    @Autowired
    private ChatConversationAppMapper chatConversationAppMapper;

    /**
     * 查询聊天会话表列表ServiceImpl
     * @param chatConversationAO
     * @return
     */
    @Override
    public ResponseResult<List<ChatConversationVO>> selectChatConversationList(ChatConversationAO chatConversationAO) {
        LOGGER.info("查询聊天会话表入参:{}", JsonUtil.convertObjectToJson(chatConversationAO));

        List<ChatConversationVO> resultData = chatConversationMapper.selectChatConversationList(chatConversationAO);
        LOGGER.info("根据条件查询聊天会话表信息列表出参:{}", JsonUtil.convertObjectToJson(resultData));

        Integer countChatConversationAll = chatConversationMapper.countChatConversationAll(chatConversationAO);
        LOGGER.info("根据条件统计结果:{}", countChatConversationAll);

        ResponseResult<List<ChatConversationVO>> result = ResultUtils.wrapResult(resultData);
        result.setTotal(countChatConversationAll);

        LOGGER.info("查询聊天会话表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    @Override
    public ResponseResult<List<ChatConversationVO>> selectLoginUserConversationList(ChatConversationAO chatConversationAO) {
        Long loginUserID = AuthenticationUtil.getUserAndRoleInfo().getUserID();

        chatConversationAO = new ChatConversationAO();
        chatConversationAO.setChatConversationActorID(loginUserID);

        ResponseResult<List<ChatConversationVO>> result = this.selectChatConversationList(chatConversationAO);

        for (ChatConversationVO conversationVO : result.getData()) {
            conversationVO.setConversationLastMessageDate(DateUtils.getReadTimeFormat(conversationVO.getConversationLastMessageDate()));
        }

        Long sum = result.getData().stream().mapToLong(ChatConversationVO::getUnreadMessagesNumber).sum();
        result.setTotal(sum);

        return result;
    }

    /**
     * 创建聊天会话表列表ServiceImpl
     * @param createChatConversationList
     * @return
     */
    @Override
    public ResponseResult<List<ChatConversationVO>> createChatConversationList(List<ChatConversationAO> createChatConversationList) {
        LOGGER.info("创建聊天会话表入参:{}", JsonUtil.convertObjectToJson(createChatConversationList));

        if (CollectionUtils.isEmpty(createChatConversationList)) {
            return ResultUtils.wrapResult();
        }

        for (ChatConversationAO chatConversationAO : createChatConversationList) {
            chatConversationAO.setChatConversationID(iPrimaryKeyService.getPrimaryKey());
        }

        Integer row = chatConversationMapper.createChatConversationList(createChatConversationList);
        LOGGER.info("成功执行{}条数据", row);

        List<ChatConversationVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(createChatConversationList, resultData, ChatConversationVO.class);

        ResponseResult<List<ChatConversationVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("创建聊天会话表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 更新聊天会话表列表ServiceImpl
     * @param updateChatConversationList
     * @return
     */
    @Override
    public ResponseResult<List<ChatConversationVO>> updateChatConversationList(List<ChatConversationAO> updateChatConversationList) {
        LOGGER.info("更新聊天会话表入参:{}", JsonUtil.convertObjectToJson(updateChatConversationList));

        if (CollectionUtils.isEmpty(updateChatConversationList)) {
            return ResultUtils.wrapResult();
        }

        Integer row = chatConversationMapper.updateChatConversationList(updateChatConversationList);
        LOGGER.info("成功执行{}条数据", row);

        List<ChatConversationVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(updateChatConversationList, resultData, ChatConversationVO.class);

        ResponseResult<List<ChatConversationVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("更新聊天会话表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 删除聊天会话表列表ServiceImpl
     * @param deleteChatConversationList
     * @return
     */
    @Override
    public ResponseResult<List<ChatConversationVO>> deleteChatConversationList(List<ChatConversationAO> deleteChatConversationList) {
        LOGGER.info("删除聊天会话表入参:{}", JsonUtil.convertObjectToJson(deleteChatConversationList));

        if (CollectionUtils.isEmpty(deleteChatConversationList)) {
            return ResultUtils.wrapResult();
        }

        Integer row = chatConversationMapper.deleteChatConversationList(deleteChatConversationList);
        LOGGER.info("成功执行{}条数据", row);

        List<ChatConversationVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(deleteChatConversationList, resultData, ChatConversationVO.class);

        ResponseResult<List<ChatConversationVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("删除聊天会话表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    @Override
    public ResponseResult<List<ChatConversationVO>> createChatConversationListByAppList(List<ChatConversationAppAO> chatConversationAppList) {
        LOGGER.info("通过申请信息生成聊天会话信息入参:{}", JsonUtil.convertObjectToJson(chatConversationAppList));

        List<ChatConversationAO> createConversationList = new ArrayList<>();

        String currentFormat = DateUtils.getCurrentFormat();

        for (ChatConversationAppAO chatConversationAppAO : chatConversationAppList) {
            // 生成用用户会话信息
            ChatConversationAO conversationOneAO = new ChatConversationAO();
            ChatConversationAO conversationTwoAO = new ChatConversationAO();
            Long primaryKey = iPrimaryKeyService.getPrimaryKey();
            conversationOneAO.setChatConversationNo(primaryKey);
            conversationTwoAO.setChatConversationNo(primaryKey);
            conversationOneAO.setChatConversationType(ConversationTypeEnum.FRIEND.getCode());
            conversationTwoAO.setChatConversationType(ConversationTypeEnum.FRIEND.getCode());
            conversationOneAO.setJoinTime(currentFormat);
            conversationTwoAO.setJoinTime(currentFormat);
            conversationOneAO.setChatConversationActorID(chatConversationAppAO.getInvitedObjectID());
            conversationTwoAO.setChatConversationActorID(chatConversationAppAO.getBeInvitedObjectID());
            createConversationList.add(conversationOneAO);
            createConversationList.add(conversationTwoAO);

            chatConversationAppAO.setInvitationStatus(ChatInvitationEnum.AGREED.getCode());
        }

        this.createChatConversationList(createConversationList);

        LOGGER.info("生成聊天会话信息持久化数据：{}", JsonUtil.convertObjectToJson(chatConversationAppList));
        Integer row = chatConversationAppMapper.updateChatConversationAppList(chatConversationAppList);
        LOGGER.info("成功执行{}条数据", row);


        List<ChatConversationVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(chatConversationAppList, resultData, ChatConversationVO.class);

        ResponseResult<List<ChatConversationVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("通过申请信息生成聊天会话信息出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

}
