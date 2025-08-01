package com.stone.elm.springboot.demo.basictech.websocket;

import com.stone.elm.springboot.demo.basictech.common.constant.CodeClsConstant;
import com.stone.elm.springboot.demo.basictech.common.constant.NumberConstant;
import com.stone.elm.springboot.demo.basictech.common.constant.RequestConstant;
import com.stone.elm.springboot.demo.basictech.common.constant.SymbolConstant;
import com.stone.elm.springboot.demo.basictech.common.exception.BusinessException;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseConstant;
import com.stone.elm.springboot.demo.basictech.common.utils.JwtUtil;
import com.stone.elm.springboot.demo.basictech.websocket.utils.WebSocketUtil;
import com.stone.elm.springboot.demo.basictech.websocket.model.WebSocketMessageModel;
import com.stone.elm.springboot.demo.basictech.websocket.model.enums.WebSocketMessageTypeEnum;
import com.stone.elm.springboot.demo.business.user.model.ao.UserInfoAO;
import com.stone.elm.springboot.demo.business.user.service.IUserInfoService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketServerHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServerHandler.class);

    private static final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // 提供 sessions 的访问方法
    public static Map<Long, WebSocketSession> getSessions() {
        return sessions;
    }

    @Autowired
    private IUserInfoService iUserInfoService;

    // 连接建立时触发
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 获取 Sec-WebSocket-Protocol 头
        String protocolHeader = session.getHandshakeHeaders().getFirst(RequestConstant.SEC_WEBSOCKET_PROTOCOL);

        if (StringUtils.isNotBlank(protocolHeader)) {
            String[] protocols = protocolHeader.split(SymbolConstant.COMMA);
            if (protocols.length >= NumberConstant.TWO && protocols[NumberConstant.ZERO].equals(RequestConstant.HEADER_TOKEN)) {
                String token = protocols[NumberConstant.ONE];
                LOGGER.info("收到 Token: {}", token);

                Long userID = isValidToken(token);
                sessions.put(userID, session);
                LOGGER.info("当前登录用户唯一标识: {}", userID);

                ArrayList<UserInfoAO> updateUserInfoList = new ArrayList<>();

                // 用户已上线
                UserInfoAO updateUser = new UserInfoAO();
                updateUser.setUserID(Long.valueOf(userID));
                updateUser.setOnlineStat(CodeClsConstant.IS_FLAG_YES);
                updateUserInfoList.add(updateUser);
                iUserInfoService.updateUserInfoList(updateUserInfoList);

                WebSocketMessageModel messageModel = new WebSocketMessageModel();
                messageModel.setMessageType(WebSocketMessageTypeEnum.REFRESH_All_MESSAGE.getCode());

                WebSocketUtil.sendMessageForUserID(userID, messageModel);

            } else {
                session.close(CloseStatus.POLICY_VIOLATION.withReason("需要令牌"));
            }
        } else {
            session.close(CloseStatus.POLICY_VIOLATION.withReason("没有找到附加协议信息"));
        }
    }

    private Long isValidToken(String token) {
        try {
            Claims claims = JwtUtil.parseJWT(token);
            return Long.valueOf(claims.getSubject());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("websocket：token解析失败!", ResponseConstant.FAIL);
        }
    }

}
