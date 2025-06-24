package com.stone.elm.springboot.demo.business.user.mapper;

import com.stone.elm.springboot.demo.business.user.model.ao.UserInfoAO;
import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    /**
     * 查询用户信息表列表Mapper
     * @param userInfoAO
     * @return
     */
    List<UserInfoVO> selectUserInfoList(UserInfoAO userInfoAO);

    /**
     * 统计用户信息表列表Mapper
     * @param userInfoAO
     * @return
     */
    Integer countUserInfoAll(UserInfoAO userInfoAO);

    /**
     * 创建用户信息表列表Mapper
     * @param createUserInfoList
     * @return
     */
    Integer createUserInfoList(@Param("createUserInfoList") List<UserInfoAO> createUserInfoList);

    /**
     * 修改用户信息表列表Mapper
     * @param updateUserInfoList
     * @return
     */
    Integer updateUserInfoList(@Param("updateUserInfoList") List<UserInfoAO> updateUserInfoList);

    /**
     * 删除用户信息表列表Mapper
     * @param deleteUserInfoList
     * @return
     */
    Integer deleteUserInfoList(@Param("deleteUserInfoList") List<UserInfoAO> deleteUserInfoList);

    Integer createUserInfo(UserInfoAO userInfoAO);
}
