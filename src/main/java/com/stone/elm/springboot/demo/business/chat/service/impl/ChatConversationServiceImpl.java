package com.stone.elm.springboot.demo.business.chat.service.impl;

import com.stone.elm.springboot.demo.basictech.common.constant.NumberConstant;
import com.stone.elm.springboot.demo.basictech.common.exception.BusinessException;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseConstant;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.response.ResultUtils;
import com.stone.elm.springboot.demo.basictech.common.service.IPrimaryKeyService;
import com.stone.elm.springboot.demo.basictech.common.utils.BeanCopyUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.JsonUtil;
import com.stone.elm.springboot.demo.business.chat.mapper.ChatConversationMapper;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationVO;
import com.stone.elm.springboot.demo.business.chat.service.IChatConversationService;
import com.stone.elm.springboot.demo.business.user.mapper.UserInfoMapper;
import com.stone.elm.springboot.demo.business.user.model.ao.UserInfoAO;
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
public class ChatConversationServiceImpl implements IChatConversationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatConversationServiceImpl.class);

    @Autowired
    private IPrimaryKeyService iPrimaryKeyService;

    @Autowired
    private ChatConversationMapper chatConversationMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

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

    /**
     * 创建聊天会话表列表ServiceImpl
     * @param chatConversationAO
     * @return
     */
    @Override
    public ResponseResult<List<ChatConversationVO>> createChatConversationList(ChatConversationAO chatConversationAO) {
        LOGGER.info("创建聊天会话表入参:{}", JsonUtil.convertObjectToJson(chatConversationAO));

        if (StringUtils.isBlank(chatConversationAO.getAddedUserName())) {
            throw new BusinessException("添加对象名称不能为空!", ResponseConstant.FAIL);
        }

        // 查询对象信息
        UserInfoAO userInfoAO = new UserInfoAO();
        userInfoAO.setUserName(chatConversationAO.getAddedUserName());
        List<UserInfoVO> userInfoList = userInfoMapper.selectUserInfoList(userInfoAO);

        if (CollectionUtils.isEmpty(userInfoList) || userInfoList.size() != NumberConstant.ONE) {
            throw new BusinessException("当前用户不存在或者不唯一!", ResponseConstant.FAIL);
        }

        // 查询当前登录人和会话对象的会话是否为同一会话编码，且不能为群组

        ChatConversationAO queryParam = new ChatConversationAO();


        ArrayList<ChatConversationAO> createChatConversationList = new ArrayList<>();

        if (CollectionUtils.isEmpty(createChatConversationList)) {
            return ResultUtils.wrapResult();
        }

        for (ChatConversationAO conversationAO : createChatConversationList) {
            conversationAO.setChatConversationID(iPrimaryKeyService.getPrimaryKey());
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

}
