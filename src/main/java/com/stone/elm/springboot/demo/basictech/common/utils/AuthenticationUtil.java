package com.stone.elm.springboot.demo.basictech.common.utils;

import com.stone.elm.springboot.demo.basictech.common.constant.RedisKeyConstant;
import com.stone.elm.springboot.demo.basictech.common.constant.RequestConstant;
import com.stone.elm.springboot.demo.basictech.common.constant.SymbolConstant;
import com.stone.elm.springboot.demo.basictech.common.exception.BusinessException;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseConstant;
import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationUtil {

    private static RedisCache redisTool;

    @Autowired
    private RedisCache redisCache;

    @PostConstruct
    public void init() {
        redisTool = redisCache;
    }

    public static UserInfoVO getUserAndRoleInfo() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();

        // 获取token
        String token = request.getHeader(RequestConstant.HEADER_TOKEN);

        // 解析token
        if (StringUtils.isBlank(token)) {
            throw new BusinessException("获取用户登录信息失败!", ResponseConstant.FAIL);
        }

        String userID;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userID = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("token解析失败!", ResponseConstant.FAIL);
        }

        if (StringUtils.isBlank(userID)) {
            throw new BusinessException("用户登录信息失效!", ResponseConstant.FAIL);
        }

        // 从redis获取用户信息
        UserInfoVO userInfo = redisTool.getCacheObject(RedisKeyConstant.USER_TOKEN_LONG + SymbolConstant.BAR + userID);

        return userInfo;

    }
}
