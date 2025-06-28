package com.stone.elm.springboot.demo.business.chat.service.impl;

import com.stone.elm.springboot.demo.basictech.common.constant.NumberConstant;
import com.stone.elm.springboot.demo.basictech.common.exception.BusinessException;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseConstant;
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
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationAppVO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationVO;
import com.stone.elm.springboot.demo.business.chat.service.IChatConversationAppService;
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
public class ChatConversationAppServiceImpl implements IChatConversationAppService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatConversationAppServiceImpl.class);

    @Autowired
    private IPrimaryKeyService iPrimaryKeyService;

    @Autowired
    private ChatConversationAppMapper chatConversationAppMapper;

    @Autowired
    private ChatConversationMapper chatConversationMapper;

    @Autowired
    private IChatConversationService iChatConversationService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 查询聊天会话申请表列表ServiceImpl
     * @param chatConversationAppAO
     * @return
     */
    @Override
    public ResponseResult<List<ChatConversationAppVO>> selectChatConversationAppList(ChatConversationAppAO chatConversationAppAO) {
        LOGGER.info("查询聊天会话申请表入参:{}", JsonUtil.convertObjectToJson(chatConversationAppAO));

        List<ChatConversationAppVO> resultData = chatConversationAppMapper.selectChatConversationAppList(chatConversationAppAO);
        LOGGER.info("根据条件查询聊天会话申请表信息列表出参:{}", JsonUtil.convertObjectToJson(resultData));

        Integer countChatConversationAppAll = chatConversationAppMapper.countChatConversationAppAll(chatConversationAppAO);
        LOGGER.info("根据条件统计结果:{}", countChatConversationAppAll);

        ResponseResult<List<ChatConversationAppVO>> result = ResultUtils.wrapResult(resultData);
        result.setTotal(countChatConversationAppAll);

        LOGGER.info("查询聊天会话申请表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    @Override
    public ResponseResult<List<ChatConversationAppVO>> selectLoginUserInvitedInfo(ChatConversationAppAO chatConversationAppAO) {
        // 获取当前登陆人ID
        UserInfoVO userInfo = AuthenticationUtil.getUserAndRoleInfo();

        chatConversationAppAO = new ChatConversationAppAO();
        chatConversationAppAO.setBeInvitedObjectID(userInfo.getUserID());
        chatConversationAppAO.setInvitationStatus(ChatInvitationEnum.PENDING.getCode());

        ResponseResult<List<ChatConversationAppVO>> responseResult = this.selectChatConversationAppList(chatConversationAppAO);

        return responseResult;
    }

    /**
     * 创建聊天会话申请表列表ServiceImpl
     * @param createChatConversationAppList
     * @return
     */
    @Override
    public ResponseResult<List<ChatConversationAppVO>> createChatConversationAppList(List<ChatConversationAppAO> createChatConversationAppList) {
        LOGGER.info("创建聊天会话申请表入参:{}", JsonUtil.convertObjectToJson(createChatConversationAppList));

        if (CollectionUtils.isEmpty(createChatConversationAppList)) {
            return ResultUtils.wrapResult();
        }

        List<ChatConversationAppAO> generateSessionList = new ArrayList<>();

        Long userOneID = AuthenticationUtil.getUserAndRoleInfo().getUserID();

        for (ChatConversationAppAO chatConversationAppAO : createChatConversationAppList) {
            if (StringUtils.isBlank(chatConversationAppAO.getBeInvitedObjectName())) {
                throw new BusinessException("添加对象名称不能为空!", ResponseConstant.FAIL);
            }

            // 查询对象信息
            UserInfoAO userInfoAO = new UserInfoAO();
            userInfoAO.setUserName(chatConversationAppAO.getBeInvitedObjectName());
            List<UserInfoVO> userInfoList = userInfoMapper.selectUserInfoList(userInfoAO);

            if (CollectionUtils.isEmpty(userInfoList) || userInfoList.size() != NumberConstant.ONE) {
                throw new BusinessException("用户名称：" + userInfoAO.getUserName() + "，不存在或者不唯一!", ResponseConstant.FAIL);
            }

            // 查询是否存在会话
            ChatConversationAO conversationAO = new ChatConversationAO();
            conversationAO.setUserOneID(userOneID);
            conversationAO.setUserTwoID(userInfoList.stream().findFirst().get().getUserID());

            List<ChatConversationVO> voList = chatConversationMapper.selectChatConversationListByUserOneAndUserTwo(conversationAO);
            LOGGER.info("查询已存在的会话出参:{}", JsonUtil.convertObjectToJson(voList));

            if (CollectionUtils.isNotEmpty(voList)) {
                throw new BusinessException("用户名称：" + userInfoAO.getUserName() + "，已存在会话中!", ResponseConstant.FAIL);
            }

            // 查看是否存在已申请信息
            chatConversationAppAO.setInvitationStatus(ChatInvitationEnum.PENDING.getCode());
            chatConversationAppAO.setInvitedObjectID(userInfoList.stream().findFirst().get().getUserID());
            chatConversationAppAO.setBeInvitedObjectID(userOneID);
            List<ChatConversationAppVO> appVOS = chatConversationAppMapper.selectChatConversationAppList(chatConversationAppAO);

            // 对方已申请,直接生成会话
            if (CollectionUtils.isNotEmpty(appVOS)) {
                ChatConversationAppAO appAO = new ChatConversationAppAO();
                BeanCopyUtil.copy(appVOS.stream().findFirst().get(), appAO);

                generateSessionList.add(appAO);
            }

            chatConversationAppAO.setInvitedObjectID(userOneID);
            chatConversationAppAO.setBeInvitedObjectID(userInfoList.stream().findFirst().get().getUserID());
            appVOS = chatConversationAppMapper.selectChatConversationAppList(chatConversationAppAO);
            if (CollectionUtils.isNotEmpty(appVOS)) {
                throw new BusinessException("用户名称：" + userInfoAO.getUserName() + "，已申请，请勿重复提交!", ResponseConstant.FAIL);
            }
        }

        if (CollectionUtils.isNotEmpty(generateSessionList)) {
            iChatConversationService.createChatConversationListByAppList(generateSessionList);
            List<ChatConversationAppVO> resultData = new ArrayList<>();
            BeanCopyUtil.copyList(generateSessionList, resultData, ChatConversationAppVO.class);
            return ResultUtils.wrapResult(resultData);
        }

        for (ChatConversationAppAO chatConversationAppAO : createChatConversationAppList) {
            chatConversationAppAO.setInvitationTime(DateUtils.getCurrentFormat());
            chatConversationAppAO.setChatConversationAppID(iPrimaryKeyService.getPrimaryKey());
        }

        Integer row = chatConversationAppMapper.createChatConversationAppList(createChatConversationAppList);
        LOGGER.info("成功执行{}条数据", row);

        List<ChatConversationAppVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(createChatConversationAppList, resultData, ChatConversationAppVO.class);

        ResponseResult<List<ChatConversationAppVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("创建聊天会话申请表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 更新聊天会话申请表列表ServiceImpl
     * @param updateChatConversationAppList
     * @return
     */
    @Override
    public ResponseResult<List<ChatConversationAppVO>> updateChatConversationAppList(List<ChatConversationAppAO> updateChatConversationAppList) {
        LOGGER.info("更新聊天会话申请表入参:{}", JsonUtil.convertObjectToJson(updateChatConversationAppList));

        if (CollectionUtils.isEmpty(updateChatConversationAppList)) {
            return ResultUtils.wrapResult();
        }

        Integer row = chatConversationAppMapper.updateChatConversationAppList(updateChatConversationAppList);
        LOGGER.info("成功执行{}条数据", row);

        List<ChatConversationAppVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(updateChatConversationAppList, resultData, ChatConversationAppVO.class);

        ResponseResult<List<ChatConversationAppVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("更新聊天会话申请表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 删除聊天会话申请表列表ServiceImpl
     * @param deleteChatConversationAppList
     * @return
     */
    @Override
    public ResponseResult<List<ChatConversationAppVO>> deleteChatConversationAppList(List<ChatConversationAppAO> deleteChatConversationAppList) {
        LOGGER.info("删除聊天会话申请表入参:{}", JsonUtil.convertObjectToJson(deleteChatConversationAppList));

        if (CollectionUtils.isEmpty(deleteChatConversationAppList)) {
            return ResultUtils.wrapResult();
        }

        Integer row = chatConversationAppMapper.deleteChatConversationAppList(deleteChatConversationAppList);
        LOGGER.info("成功执行{}条数据", row);

        List<ChatConversationAppVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(deleteChatConversationAppList, resultData, ChatConversationAppVO.class);

        ResponseResult<List<ChatConversationAppVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("删除聊天会话申请表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

}
