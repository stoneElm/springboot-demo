package com.stone.elm.springboot.demo.business.user.service.impl;

import com.stone.elm.springboot.demo.basictech.common.constant.RedisKeyConstant;
import com.stone.elm.springboot.demo.basictech.common.constant.SymbolConstant;
import com.stone.elm.springboot.demo.basictech.common.exception.BusinessException;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseConstant;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.response.ResultUtils;
import com.stone.elm.springboot.demo.basictech.common.utils.JsonUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.JwtUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.PatternValidUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.RedisCache;
import com.stone.elm.springboot.demo.business.user.mapper.UserInfoMapper;
import com.stone.elm.springboot.demo.business.user.model.ao.LoginInfoAO;
import com.stone.elm.springboot.demo.business.user.model.ao.UserInfoAO;
import com.stone.elm.springboot.demo.business.user.model.enums.LoginModelEnum;
import com.stone.elm.springboot.demo.business.user.model.vo.LoginInfoVO;
import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;
import com.stone.elm.springboot.demo.business.user.service.IUserLoginService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserLoginServiceImpl implements IUserLoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult<LoginInfoVO> login(LoginInfoAO loginInfoAO) {
        LOGGER.info("用户登陆操作信息总体入参:{}", JsonUtil.convertObjectToJson(loginInfoAO));

        checkLoginParam(loginInfoAO);

        LoginInfoVO resultData = new LoginInfoVO();

        // 根据登录信息查询用户信息
        UserInfoVO userInfo = getLoginUserInfo(loginInfoAO);

        // 校验密码或验证码
        if (StringUtils.equals(LoginModelEnum.LOGIN_MODEL_PASSWORD.getLoginModelCode(), loginInfoAO.getLoginModel())) {
            // 进行用户认证
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginInfoAO.getUserName(), loginInfoAO.getPassword());

            // 会调用 UserDetailsServiceImpl 获取用户信息
            Authentication authenticate;
            try {
                authenticate = authenticationManager.authenticate(authenticationToken);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage(), ResponseConstant.FAIL);
            }

            if (Objects.isNull(authenticate)) {
                throw new BusinessException("用户名或密码错误!", ResponseConstant.FAIL);
            }
        } else {
            // 根据手机号获取验证码
            String captcha = redisCache.getCacheObject("captcha-" + userInfo.getUserPhone());

            if (StringUtils.isBlank(captcha) || StringUtils.equals(captcha, loginInfoAO.getVerificationCode())) {
                throw new BusinessException("验证码错误!", ResponseConstant.FAIL);
            }
        }

        // 通过了，生成jwt
        String jwt = JwtUtil.createJWT(userInfo.getUserID().toString(), JwtUtil.JWT_TTL_ONE_DAY);
        resultData.setToken(jwt);
        String fileJwt = JwtUtil.createJWT(userInfo.getUserID().toString(), JwtUtil.JWT_TTL_ONE_DAY);
        resultData.setFileToken(fileJwt);

        UserInfoVO redisUserInfo = new UserInfoVO();
        redisUserInfo.setUserID(userInfo.getUserID());
        redisUserInfo.setUserName(userInfo.getUserName());

        //将用户信息存入redis
        redisCache.setCacheObject(RedisKeyConstant.USER_TOKEN_LONG + SymbolConstant.BAR + userInfo.getUserID(), redisUserInfo);


        return ResultUtils.wrapResult(resultData);
    }

    @Override
    public ResponseResult<List<UserInfoVO>> register(UserInfoAO userInfoAO) {
        LOGGER.info("用户注册信息总体入参:{}", JsonUtil.convertObjectToJson(userInfoAO));

        checkRegisterParam(userInfoAO);

        // 获取密码哈希
        String encodedPassword = passwordEncoder.encode(userInfoAO.getPassword());
        userInfoAO.setPassword(encodedPassword);

        // 持久化操作
        userInfoMapper.createUserInfo(userInfoAO);

        return null;
    }

    @Override
    public ResponseResult<Object> logout(UserInfoAO userInfoAO) {
        // 删除 SecurityContextHolder 中的用户ID
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserInfoVO principal = (UserInfoVO) authentication.getPrincipal();

        Long userID = principal.getUserID();

        // 删除redis值
        redisCache.deleteObject(RedisKeyConstant.USER_TOKEN_LONG + SymbolConstant.BAR + userID);

        ResponseResult<Object> result = ResultUtils.wrapResult();

        result.setMessage("登出成功! ");

        return result;
    }

    private void checkLoginParam(LoginInfoAO loginInfoAO) {
        if (!StringUtils.equals(LoginModelEnum.LOGIN_MODEL_PASSWORD.getLoginModelCode(), loginInfoAO.getLoginModel())
                && !StringUtils.equals(LoginModelEnum.LOGIN_MODEL_PHONE.getLoginModelCode(), loginInfoAO.getLoginModel())) {
            throw new BusinessException("登陆模式不存在", ResponseConstant.FAIL);
        }

        if (StringUtils.equals(LoginModelEnum.LOGIN_MODEL_PASSWORD.getLoginModelCode(), loginInfoAO.getLoginModel())
                && StringUtils.isBlank(loginInfoAO.getUserName())) {
            throw new BusinessException("用户名不能为空", ResponseConstant.FAIL);
        }

        if (StringUtils.equals(LoginModelEnum.LOGIN_MODEL_PHONE.getLoginModelCode(), loginInfoAO.getLoginModel())
                && !PatternValidUtil.isValidPhoneNumber(loginInfoAO.getUserPhone())) {
            throw new BusinessException("手机号码不正确", ResponseConstant.FAIL);
        }
    }

    private UserInfoVO getLoginUserInfo(LoginInfoAO loginInfoAO) {

        List<UserInfoVO> userInfoVOList = new ArrayList<>();

        if (StringUtils.equals(LoginModelEnum.LOGIN_MODEL_PASSWORD.getLoginModelCode(), loginInfoAO.getLoginModel())) {
            // 账号密码登录
            UserInfoAO selectUserInfoParam = new UserInfoAO();
            selectUserInfoParam.setUserName(loginInfoAO.getUserName());
            userInfoVOList = userInfoMapper.selectUserInfoList(selectUserInfoParam);
        } else {
            // 手机验证登录
            UserInfoAO selectUserInfoParam = new UserInfoAO();
            selectUserInfoParam.setUserPhone(loginInfoAO.getUserPhone());
            userInfoVOList = userInfoMapper.selectUserInfoList(selectUserInfoParam);
        }

        if (CollectionUtils.isEmpty(userInfoVOList)) {
            throw new BusinessException("当前用户不存在!", ResponseConstant.FAIL);
        }

        if (userInfoVOList.size() > 1) {
            throw new BusinessException("当前用户数据有误,请联系管理员!", ResponseConstant.FAIL);
        }

        return userInfoVOList.stream().findFirst().get();
    }

    private void checkRegisterParam(UserInfoAO userInfoAO) {
        UserInfoAO selectUserInfo = new UserInfoAO();

        // 校验内容
        List<UserInfoVO> userInfoVOList1 = new ArrayList<>();
        if (StringUtils.isNotBlank(userInfoAO.getUserPhone())) {
            selectUserInfo.setUserPhone(userInfoAO.getUserPhone());
            userInfoVOList1 = userInfoMapper.selectUserInfoList(selectUserInfo);

            if (CollectionUtils.isNotEmpty(userInfoVOList1)) {
                throw new BusinessException("手机号码已被注册!", ResponseConstant.FAIL);
            }
        }

        List<UserInfoVO> userInfoVOList2 = new ArrayList<>();
        if (StringUtils.isNotBlank(userInfoAO.getUserName())) {
            selectUserInfo = new UserInfoAO();
            selectUserInfo.setUserName(userInfoAO.getUserName());
            userInfoVOList2 = userInfoMapper.selectUserInfoList(selectUserInfo);

            if (CollectionUtils.isNotEmpty(userInfoVOList2)) {
                throw new BusinessException("用户名已被注册!", ResponseConstant.FAIL);
            }
        }
    }
}
