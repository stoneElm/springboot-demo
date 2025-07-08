package com.stone.elm.springboot.demo.business.chat.service.impl;

import com.stone.elm.springboot.demo.basictech.common.constant.CodeClsConstant;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.response.ResultUtils;
import com.stone.elm.springboot.demo.basictech.common.service.IPrimaryKeyService;
import com.stone.elm.springboot.demo.basictech.common.types.ValidList;
import com.stone.elm.springboot.demo.basictech.common.utils.BeanCopyUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.DateUtils;
import com.stone.elm.springboot.demo.basictech.common.utils.JsonUtil;
import com.stone.elm.springboot.demo.business.chat.mapper.ChatConversationMessageRelatedMapper;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationMessageRelatedAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationMessageRelatedVO;
import com.stone.elm.springboot.demo.business.chat.service.IChatConversationMessageRelatedService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatConversationMessageRelatedServiceImpl implements IChatConversationMessageRelatedService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatConversationMessageRelatedServiceImpl.class);

    @Autowired
    private IPrimaryKeyService iPrimaryKeyService;

    @Autowired
    private ChatConversationMessageRelatedMapper chatConversationMessageRelatedMapper;

    /**
     * 查询聊天会话关联消息表列表ServiceImpl
     * @param chatConversationMessageRelatedAO
     * @return
     */
    @Override
    public ResponseResult<List<ChatConversationMessageRelatedVO>> selectChatConversationMessageRelatedList(ChatConversationMessageRelatedAO chatConversationMessageRelatedAO) {
        LOGGER.info("查询聊天会话关联消息表入参:{}", JsonUtil.convertObjectToJson(chatConversationMessageRelatedAO));

        List<ChatConversationMessageRelatedVO> resultData = chatConversationMessageRelatedMapper.selectChatConversationMessageRelatedList(chatConversationMessageRelatedAO);
        LOGGER.info("根据条件查询聊天会话关联消息表信息列表出参:{}", JsonUtil.convertObjectToJson(resultData));

        Integer countChatConversationMessageRelatedAll = chatConversationMessageRelatedMapper.countChatConversationMessageRelatedAll(chatConversationMessageRelatedAO);
        LOGGER.info("根据条件统计结果:{}", countChatConversationMessageRelatedAll);

        ResponseResult<List<ChatConversationMessageRelatedVO>> result = ResultUtils.wrapResult(resultData);
        result.setTotal(countChatConversationMessageRelatedAll);

        LOGGER.info("查询聊天会话关联消息表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 创建聊天会话关联消息表列表ServiceImpl
     * @param createChatConversationMessageRelatedList
     * @return
     */
    @Override
    public ResponseResult<List<ChatConversationMessageRelatedVO>> createChatConversationMessageRelatedList(List<ChatConversationMessageRelatedAO> createChatConversationMessageRelatedList) {
        LOGGER.info("创建聊天会话关联消息表入参:{}", JsonUtil.convertObjectToJson(createChatConversationMessageRelatedList));

        if (CollectionUtils.isEmpty(createChatConversationMessageRelatedList)) {
            return ResultUtils.wrapResult();
        }

        for (ChatConversationMessageRelatedAO chatConversationMessageRelatedAO : createChatConversationMessageRelatedList) {
            chatConversationMessageRelatedAO.setChatConversationMessageRelatedID(iPrimaryKeyService.getPrimaryKey());
        }

        Integer row = chatConversationMessageRelatedMapper.createChatConversationMessageRelatedList(createChatConversationMessageRelatedList);
        LOGGER.info("成功执行{}条数据", row);

        List<ChatConversationMessageRelatedVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(createChatConversationMessageRelatedList, resultData, ChatConversationMessageRelatedVO.class);

        ResponseResult<List<ChatConversationMessageRelatedVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("创建聊天会话关联消息表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 更新聊天会话关联消息表列表ServiceImpl
     * @param updateChatConversationMessageRelatedList
     * @return
     */
    @Override
    public ResponseResult<List<ChatConversationMessageRelatedVO>> updateChatConversationMessageRelatedList(List<ChatConversationMessageRelatedAO> updateChatConversationMessageRelatedList) {
        LOGGER.info("更新聊天会话关联消息表入参:{}", JsonUtil.convertObjectToJson(updateChatConversationMessageRelatedList));

        if (CollectionUtils.isEmpty(updateChatConversationMessageRelatedList)) {
            return ResultUtils.wrapResult();
        }

        Integer row = chatConversationMessageRelatedMapper.updateChatConversationMessageRelatedList(updateChatConversationMessageRelatedList);
        LOGGER.info("成功执行{}条数据", row);

        List<ChatConversationMessageRelatedVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(updateChatConversationMessageRelatedList, resultData, ChatConversationMessageRelatedVO.class);

        ResponseResult<List<ChatConversationMessageRelatedVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("更新聊天会话关联消息表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    @Override
    public ResponseResult<List<ChatConversationMessageRelatedVO>> markReadMessageForCurrentLogin(ValidList<ChatConversationMessageRelatedAO> chatConversationMessageRelatedList) {
        LOGGER.info("更新聊天会话关联消息表-当前会话消息已读-入参:{}", JsonUtil.convertObjectToJson(chatConversationMessageRelatedList));

        if (CollectionUtils.isEmpty(chatConversationMessageRelatedList)) {
            return ResultUtils.wrapResult();
        }

        String currentFormat = DateUtils.getCurrentFormat();

        ArrayList<ChatConversationMessageRelatedAO> updateList = new ArrayList<>();

        for (ChatConversationMessageRelatedAO relatedAO : chatConversationMessageRelatedList) {
            ChatConversationMessageRelatedAO queryParam = new ChatConversationMessageRelatedAO();
            queryParam.setChatConversationID(relatedAO.getChatConversationID());
            queryParam.setIsRead(CodeClsConstant.IS_FLAG_NO);

            List<ChatConversationMessageRelatedVO> resultData = chatConversationMessageRelatedMapper.selectChatConversationMessageRelatedList(queryParam);
            LOGGER.info("根据条件查询聊天会话关联消息表信息列表出参:{}", JsonUtil.convertObjectToJson(resultData));

            for (ChatConversationMessageRelatedVO datum : resultData) {
                ChatConversationMessageRelatedAO updateAO = new ChatConversationMessageRelatedAO();
                updateAO.setChatConversationMessageRelatedID(datum.getChatConversationMessageRelatedID());
                updateAO.setIsRead(CodeClsConstant.IS_FLAG_YES);
                updateAO.setReadDate(currentFormat);

                updateList.add(updateAO);
            }
        }

        return this.updateChatConversationMessageRelatedList(updateList);
    }

    /**
     * 删除聊天会话关联消息表列表ServiceImpl
     * @param deleteChatConversationMessageRelatedList
     * @return
     */
    @Override
    public ResponseResult<List<ChatConversationMessageRelatedVO>> deleteChatConversationMessageRelatedList(List<ChatConversationMessageRelatedAO> deleteChatConversationMessageRelatedList) {
        LOGGER.info("删除聊天会话关联消息表入参:{}", JsonUtil.convertObjectToJson(deleteChatConversationMessageRelatedList));

        if (CollectionUtils.isEmpty(deleteChatConversationMessageRelatedList)) {
            return ResultUtils.wrapResult();
        }

        Integer row = chatConversationMessageRelatedMapper.deleteChatConversationMessageRelatedList(deleteChatConversationMessageRelatedList);
        LOGGER.info("成功执行{}条数据", row);

        List<ChatConversationMessageRelatedVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(deleteChatConversationMessageRelatedList, resultData, ChatConversationMessageRelatedVO.class);

        ResponseResult<List<ChatConversationMessageRelatedVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("删除聊天会话关联消息表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

}
