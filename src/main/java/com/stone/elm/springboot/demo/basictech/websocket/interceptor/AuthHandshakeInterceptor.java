package com.stone.elm.springboot.demo.basictech.websocket.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

public class AuthHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        // 从请求中获取token并验证
        String token = request.getHeaders().getFirst("Authorization");
        // 验证逻辑...
        attributes.put("userId", token); // 将用户ID存入attributes
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}
