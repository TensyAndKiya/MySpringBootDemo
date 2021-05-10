package com.clei.bo;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 聊天用户
 *
 * @author KIyA
 * @date 2021-05-10
 */
public class ChatUser {

    /**
     * 用户信息
     */
    private final static ConcurrentHashMap<String, ChatUser> CHAT_USER_MAP = new ConcurrentHashMap<>();

    /**
     * WebSocket Session
     */
    private Session session;

    /**
     * 添加当前用户
     *
     * @param session WebSocket Session
     */
    public static void add(Session session) {
        String id = session.getId();
        ChatUser user = new ChatUser(session);
        CHAT_USER_MAP.put(id, user);
    }

    /**
     * 去掉当前用户
     *
     * @param session WebSocket Session
     */
    public static void remove(Session session) {
        String id = session.getId();
        CHAT_USER_MAP.remove(id);
    }

    /**
     * 发送消息给单个用户
     *
     * @param session WebSocket Session
     * @param message 消息
     * @throws IOException
     */
    public static void sendMessage(Session session, String message) throws Exception {
        String id = session.getId();
        ChatUser user = CHAT_USER_MAP.get(id);
        if (null != user) {
            user.getSession().getBasicRemote().sendText(message);
            // user.getSession().getAsyncRemote().sendText(message);
        }
    }

    /**
     * 发送消息给所有用户
     *
     * @param message 消息
     * @throws Exception
     */
    public static void sendMessage2Group(String message) throws Exception {
        for (ChatUser user : CHAT_USER_MAP.values()) {
            user.getSession().getBasicRemote().sendText(message);
        }
    }

    /**
     * 发送消息给除了某些用户之外的其它用户
     *
     * @param message 消息
     * @param except  不发送消息的用户
     * @throws Exception
     */
    public static void sendMessage2Group(String message, Session... except) throws Exception {
        Set<String> exceptIdSet = Arrays.stream(except)
                .map(Session::getId).collect(Collectors.toSet());
        for (ChatUser user : CHAT_USER_MAP.values()) {
            String id = user.getSession().getId();
            if (!exceptIdSet.contains(id)) {
                user.getSession().getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 获取当前在线用户数量
     *
     * @return
     */
    public static int getOnlineCount() {
        return CHAT_USER_MAP.keySet().size();
    }

    /**
     * 私有构造
     *
     * @param session
     */
    private ChatUser(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
