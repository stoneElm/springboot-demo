package com.stone.elm.springboot.demo.basictech.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/webSocket/{userId}")
@Component
public class ChatRoom {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatRoom.class);

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。但为了实现服务端与单一客户端通信，用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<Map<String, ChatRoom>> mapSocket = new CopyOnWriteArraySet<Map<String, ChatRoom>>();

    private static CopyOnWriteArraySet<Map<String, Session>> mapSession = new CopyOnWriteArraySet<Map<String, Session>>();

    private static Map<String, String> userMap = new HashMap<>();

    //当前用户
    private String userId;

    /**
     * 连接时执行
     * @param userId 当前用户名称
     * @param session 当前用户session
     * @throws Exception
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) throws Exception {
        //存放当前用户的ChatRoom对象
        Map<String, ChatRoom> wsSocket = new HashMap<String, ChatRoom>();

        //存放当前用户的session对象
        Map<String, Session> wsSession = new HashMap<String, Session>();

        //用map建立用户和当前用户的MyWebSocket对象关系
        wsSocket.put(userId, this);
        mapSocket.add(wsSocket);

        //用map建立用户和当前用户的session对象关系
        wsSession.put(userId, session);
        mapSession.add(wsSession);

        userMap.put(userId, userId);
        this.userId = userId;
        LOGGER.info("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    public static synchronized int getOnlineCount() {
        return mapSocket.size();
    }
}
