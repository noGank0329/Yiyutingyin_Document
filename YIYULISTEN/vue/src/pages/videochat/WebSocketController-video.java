package com.example.webrtc.controller;

import com.example.webrtc.config.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

// 私信聊天的控制器
@RestController
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private AtomicInteger i=new AtomicInteger(1);
    @RequestMapping("/user")
    public String findUser(){
        return "00"+i.decrementAndGet();
    }
    @MessageMapping("/api/chat")
    //在springmvc 中可以直接获得principal,principal 中包含当前用户的信息
    public void handleChat(Principal principal, Message messagePara) {

        String currentUserName = principal.getName();
        System.out.println(currentUserName);

        try {
            messagePara.setFrom(principal.getName());
            System.out.println("from" + messagePara.getFrom());
            messagingTemplate.convertAndSendToUser(messagePara.getTo(),
                    "/queue/notifications",
                    messagePara);
        } catch (Exception e) {
            // 打印异常
            e.printStackTrace();
        }
    }
}

