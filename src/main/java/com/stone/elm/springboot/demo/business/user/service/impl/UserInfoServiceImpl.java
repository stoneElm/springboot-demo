package com.stone.elm.springboot.demo.business.user.service.impl;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.response.ResultUtils;
import com.stone.elm.springboot.demo.basictech.common.service.IPrimaryKeyService;
import com.stone.elm.springboot.demo.basictech.common.utils.BeanCopyUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.JsonUtil;
import com.stone.elm.springboot.demo.business.user.mapper.UserInfoMapper;
import com.stone.elm.springboot.demo.business.user.model.ao.UserInfoAO;
import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;
import com.stone.elm.springboot.demo.business.user.service.IUserInfoService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoServiceImpl implements IUserInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private IPrimaryKeyService iPrimaryKeyService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 查询用户信息表列表ServiceImpl
     * @param userInfoAO
     * @return
     */
    @Override
    public ResponseResult<List<UserInfoVO>> selectUserInfoList(UserInfoAO userInfoAO) {
        LOGGER.info("查询用户信息表入参:{}", JsonUtil.convertObjectToJson(userInfoAO));

        List<UserInfoVO> resultData = userInfoMapper.selectUserInfoList(userInfoAO);
        LOGGER.info("根据条件查询用户信息表信息列表出参:{}", JsonUtil.convertObjectToJson(resultData));

        Integer countUserInfoAll = userInfoMapper.countUserInfoAll(userInfoAO);
        LOGGER.info("根据条件统计结果:{}", countUserInfoAll);

        ResponseResult<List<UserInfoVO>> result = ResultUtils.wrapResult(resultData);
        result.setTotal(countUserInfoAll);

        LOGGER.info("查询用户信息表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 创建用户信息表列表ServiceImpl
     * @param createUserInfoList
     * @return
     */
    @Override
    public ResponseResult<List<UserInfoVO>> createUserInfoList(List<UserInfoAO> createUserInfoList) {
        LOGGER.info("创建用户信息表入参:{}", JsonUtil.convertObjectToJson(createUserInfoList));

        if (CollectionUtils.isEmpty(createUserInfoList)) {
            return ResultUtils.wrapResult();
        }

        for (UserInfoAO userInfoAO : createUserInfoList) {
            userInfoAO.setUserID(iPrimaryKeyService.getPrimaryKey());
        }

        Integer row = userInfoMapper.createUserInfoList(createUserInfoList);
        LOGGER.info("成功执行{}条数据", row);

        List<UserInfoVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(createUserInfoList, resultData, UserInfoVO.class);

        ResponseResult<List<UserInfoVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("创建用户信息表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 更新用户信息表列表ServiceImpl
     * @param updateUserInfoList
     * @return
     */
    @Override
    public ResponseResult<List<UserInfoVO>> updateUserInfoList(List<UserInfoAO> updateUserInfoList) {
        LOGGER.info("更新用户信息表入参:{}", JsonUtil.convertObjectToJson(updateUserInfoList));

        if (CollectionUtils.isEmpty(updateUserInfoList)) {
            return ResultUtils.wrapResult();
        }

        Integer row = userInfoMapper.updateUserInfoList(updateUserInfoList);
        LOGGER.info("成功执行{}条数据", row);

        List<UserInfoVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(updateUserInfoList, resultData, UserInfoVO.class);

        ResponseResult<List<UserInfoVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("更新用户信息表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 删除用户信息表列表ServiceImpl
     * @param deleteUserInfoList
     * @return
     */
    @Override
    public ResponseResult<List<UserInfoVO>> deleteUserInfoList(List<UserInfoAO> deleteUserInfoList) {
        LOGGER.info("删除用户信息表入参:{}", JsonUtil.convertObjectToJson(deleteUserInfoList));

        if (CollectionUtils.isEmpty(deleteUserInfoList)) {
            return ResultUtils.wrapResult();
        }

        Integer row = userInfoMapper.deleteUserInfoList(deleteUserInfoList);
        LOGGER.info("成功执行{}条数据", row);

        List<UserInfoVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(deleteUserInfoList, resultData, UserInfoVO.class);

        ResponseResult<List<UserInfoVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("删除用户信息表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

}
