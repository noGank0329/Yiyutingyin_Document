package com.yiyu.component;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.websocket.*;
import jakarta.websocket.CloseReason;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/imserver/{username}/{usertype}")
public class WebSocketServer {
    public class User {
        private String username;
        private int usertype;
        private int userstate;

        public User(String username, int usertype) {
            this.username = username;
            this.usertype = usertype;
            this.userstate = 0; // 初始状态为0
        }

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getUsertype() {
            return usertype;
        }

        public void setUsertype(int usertype) {
            this.usertype = usertype;
        }

        public int getUserstate() {
            return userstate;
        }

        public void setUserstate(int userstate) {
            this.userstate = userstate;
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServer.class);

    private Session session;
    private User user;

    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();
    public static final Map<String, User> userMap = new ConcurrentHashMap<>();
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String username) throws IOException {
        LOGGER.info("服务端收到用户username={}的消息:{}", username, message);
        JSONObject obj = JSONUtil.parseObj(message);
        String type = obj.getStr("type");
        String text = obj.getStr("text");


        if ("check".equals(type)) {
            String toUsername = obj.getStr("to");
            Session toSession = sessionMap.get(username);
            if (toSession != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("type", "check");
                jsonObject.set("from", username);
                jsonObject.set("to", toUsername);
                User toUser = userMap.get(toUsername);
                LOGGER.info("123123:{}", toUser.userstate);
                if (toUser.userstate == 1) {
                    LOGGER.info("重复连接请求");
                    jsonObject.set("keyword", 1); // 返回关键字1
                } else {
                    jsonObject.set("keyword", 0); // 返回关键字0
                }
                this.sendMessage(jsonObject.toString(), toSession);
            }
        } else if ("request".equals(type)) {
            String toUsername = obj.getStr("to");
            Session toSession = sessionMap.get(toUsername);
            if (toSession != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("type", "request");
                jsonObject.set("from", username);
                jsonObject.set("to", toUsername);
                this.sendMessage(jsonObject.toString(), toSession);
                LOGGER.info("发送请求连接消息给用户username={}", toUsername);

            } else {
                LOGGER.info("发送失败，未找到用户username={}的session", toUsername);
            }
        } else if ("confirm".equals(type)) {
            String toUsername = obj.getStr("to");
            Session toSession = sessionMap.get(toUsername);
            if (toSession != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("type", "confirm");
                jsonObject.set("from", username);
                jsonObject.set("to", toUsername);

                User fromUser = userMap.get(username);
                User toUser = userMap.get(toUsername);
                if (fromUser != null && toUser != null) {
                    fromUser.setUserstate(1);
                    toUser.setUserstate(1);

                    JSONObject result = new JSONObject();
                    JSONArray array = new JSONArray();
                    result.set("users", array);
                    for (User u : userMap.values()) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.set("username", u.getUsername());
                        jsonObject1.set("usertype", u.getUsertype());
                        jsonObject1.set("userstate", u.getUserstate());
                        array.add(jsonObject1);
                    }
                    sendAllMessage(JSONUtil.toJsonStr(result));
                }
                LOGGER.info("state1={}",fromUser.userstate);
                LOGGER.info("state2={}",toUser.userstate);
                this.sendMessage(jsonObject.toString(), toSession);
                this.sendMessage(jsonObject.toString(), session);
                LOGGER.info("发送确认连接消息给用户username={}", toUsername);
            }
            else {
                LOGGER.info("发送失败，未找到用户username={}的session", toUsername);
            }
        }
        else if ("disconnect".equals(type)) {
            String toUsername = obj.getStr("to");
            Session toSession = sessionMap.get(toUsername);
            if (toSession != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("type", "disconnect");
                jsonObject.set("from", username);
                jsonObject.set("to", toUsername);


                User fromUser = userMap.get(username);
                User toUser = userMap.get(toUsername);

                if (fromUser != null && toUser != null) {
                    fromUser.setUserstate(0);
                    toUser.setUserstate(0);

                    JSONObject result = new JSONObject();
                    JSONArray array = new JSONArray();
                    result.set("users", array);
                    for (User u : userMap.values()) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.set("username", u.getUsername());
                        jsonObject1.set("usertype", u.getUsertype());
                        jsonObject1.set("userstate", u.getUserstate());
                        array.add(jsonObject1);
                    }
                    sendAllMessage(JSONUtil.toJsonStr(result));
                }
                this.sendMessage(jsonObject.toString(), toSession);
            }
        }
        else if ("close".equals(type)) {
            JSONObject result = new JSONObject();
            JSONArray array = new JSONArray();
            result.set("users", array);
            for (User u : userMap.values()) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.set("username", u.getUsername());
                jsonObject1.set("usertype", u.getUsertype());
                jsonObject1.set("userstate", u.getUserstate());
                array.add(jsonObject1);
            }
            sendAllMessage(JSONUtil.toJsonStr(result));
            LOGGER.info("消息已经发送");
        }
        else {
            String toUsername = obj.getStr("to");
            Session toSession = sessionMap.get(toUsername);
            if (toSession != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("from", username);
                jsonObject.set("text", text);
                this.sendMessage(jsonObject.toString(), toSession);
                LOGGER.info("发送给用户username={}，消息：{}", toUsername, jsonObject.toString());

                // 更新用户状态
                User toUser = userMap.get(toUsername);
                User fromUser = userMap.get(username);
                LOGGER.info("state1={}",fromUser.userstate);
                LOGGER.info("state2={}",toUser.userstate);
                if (toUser != null) {
                    toUser.setUserstate(1); // 设置目标用户状态为1
                }

                if (fromUser != null) {
                    fromUser.setUserstate(1); // 设置当前用户状态为1
                }
            }
        }
    }


    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("usertype") int usertype, EndpointConfig endpointConfig){
        User user = new User(username, usertype);
        userMap.put(username, user);
        sessionMap.put(username, session);
        this.session = session;
        this.user = user;
        LOGGER.info("[websocket] 新的连接：id={}", this.session.getId());
        LOGGER.info("有新用户加入，username={}, usertype={}, 当前在线人数为：{}", username, usertype, sessionMap.size());

        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("users", array);
        for (User u : userMap.values()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("username", u.getUsername());
            jsonObject.set("usertype", u.getUsertype());
            jsonObject.set("userstate", u.getUserstate());
            array.add(jsonObject);
        }
        sendAllMessage(JSONUtil.toJsonStr(result));
    }

    @OnClose
    public void onClose(CloseReason closeReason, @PathParam("username") String username){
        sessionMap.remove(username);
        userMap.remove(username);
        LOGGER.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", username, sessionMap.size());
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("users", array);
        for (User u : userMap.values()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("username", u.getUsername());
            jsonObject.set("usertype", u.getUsertype());
            jsonObject.set("userstate", u.getUserstate());
            array.add(jsonObject);
        }
        sendAllMessage(JSONUtil.toJsonStr(result));
    }

    @OnError
    public void onError(Throwable throwable) throws IOException {
        LOGGER.info("[websocket] 连接异常：id={}，throwable={}", this.session.getId(), throwable.getMessage());
        this.session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
    }

    private void sendMessage(String message, Session toSession) {
        try {
            LOGGER.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            JSONObject obj = JSONUtil.parseObj(message);
            String toUsername = obj.getStr("from");
            User fromUser = userMap.get(toUsername);
            LOGGER.info("state11111={}",fromUser.userstate);

            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            LOGGER.error("服务端发送消息给客户端失败", e);
        }
    }

    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                LOGGER.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            LOGGER.error("服务端发送消息给客户端失败", e);
        }
    }
}
