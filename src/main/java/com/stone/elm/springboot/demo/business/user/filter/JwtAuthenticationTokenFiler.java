package com.stone.elm.springboot.demo.business.user.filter;

import com.stone.elm.springboot.demo.basictech.common.constant.RedisKeyConstant;
import com.stone.elm.springboot.demo.basictech.common.constant.RequestConstant;
import com.stone.elm.springboot.demo.basictech.common.constant.SymbolConstant;
import com.stone.elm.springboot.demo.basictech.common.exception.BusinessException;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseConstant;
import com.stone.elm.springboot.demo.basictech.common.utils.JwtUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.RedisCache;
import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 认证过滤器
 */
@Component
public class JwtAuthenticationTokenFiler extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader(RequestConstant.HEADER_TOKEN);

        Boolean previewFlag = request.getRequestURI().startsWith("/attachment/files/filePreview");

        if (StringUtils.isBlank(token) && previewFlag) {
            token = request.getParameter(RequestConstant.STONE_FILE_TOKEN);
        }

        if (StringUtils.isBlank(token)) {
            // 放行, 交给其他过滤器处理
            filterChain.doFilter(request, response);
            return;
        }

        // 解析token
        String userID;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userID = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("登录鉴权：token解析失败!", ResponseConstant.FAIL);
        }

        if (StringUtils.isBlank(userID)) {
            throw new BusinessException("登录鉴权：用户登录信息失效!", ResponseConstant.FAIL);
        }

        // 从redis获取用户信息
        UserInfoVO userInfo = redisCache.getCacheObject(RedisKeyConstant.USER_TOKEN_LONG + SymbolConstant.BAR + userID);

        if (Objects.isNull(userInfo)) {
            throw new BusinessException("登录鉴权：用户登录信息不存在!", ResponseConstant.FAIL);
        }

        // 存入 SecurityContextHolder

        // todo 获取权限信息到 SecurityContextHolder

        /**
         * 三个参数
         * Principal：代表用户的信息，如用户名、电子邮件等。
         * Credentials：代表用户的凭证，通常是用户密码的哈希值。
         * Authorities：代表用户的角色或权限。
         */
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userInfo, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}
