package com.stone.elm.springboot.demo.basictech.websocket.interceptor;

import com.stone.elm.springboot.demo.basictech.common.constant.NumberConstant;
import com.stone.elm.springboot.demo.basictech.common.constant.RequestConstant;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.List;
import java.util.Map;

public class AuthHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        // 从请求中获取token并验证
        // String protocolHeader  = request.getHeaders().getFirst(RequestConstant.SEC_WEBSOCKET_PROTOCOL);

        // 检查请求头中的 Sec-WebSocket-Protocol
        if (request.getHeaders().containsKey(RequestConstant.SEC_WEBSOCKET_PROTOCOL)) {
            List<String> protocols = request.getHeaders().get(RequestConstant.SEC_WEBSOCKET_PROTOCOL);
            if (protocols.get(NumberConstant.ZERO).contains(RequestConstant.HEADER_TOKEN)) {
                // 告诉客户端使用 "Stone-Token"
                response.getHeaders().set(RequestConstant.SEC_WEBSOCKET_PROTOCOL, RequestConstant.HEADER_TOKEN);
                return super.beforeHandshake(request, response, wsHandler, attributes);
            }
        }
        return false; // 拒绝握手
    }
}
