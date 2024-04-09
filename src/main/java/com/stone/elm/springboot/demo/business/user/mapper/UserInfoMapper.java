package com.stone.elm.springboot.demo.business.user.mapper;

import com.stone.elm.springboot.demo.business.user.model.ao.UserInfoAO;
import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    List<UserInfoVO> selectUserInfoList(UserInfoAO userInfoAO);

    Integer createUserInfo(UserInfoAO userInfoAO);
}
