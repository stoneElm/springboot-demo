package com.stone.elm.springboot.demo.business.user.service;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.business.user.model.ao.UserInfoAO;
import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;

import java.util.List;

public interface IUserInfoService {

    /**
     * 查询用户信息表列表Service
     * @param userInfoAO
     * @return
     */
    ResponseResult<List<UserInfoVO>> selectUserInfoList(UserInfoAO userInfoAO);

    /**
     * 创建用户信息表列表Service
     * @param userInfoList
     * @return
     */
    ResponseResult<List<UserInfoVO>> createUserInfoList(List<UserInfoAO> userInfoList);

    /**
     * 更新用户信息表列表Service
     * @param userInfoList
     * @return
     */
    ResponseResult<List<UserInfoVO>> updateUserInfoList(List<UserInfoAO> userInfoList);

    /**
     * 删除用户信息表列表Service
     * @param userInfoList
     * @return
     */
    ResponseResult<List<UserInfoVO>> deleteUserInfoList(List<UserInfoAO> userInfoList);

}
