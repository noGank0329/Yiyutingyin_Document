package com.example.webrtc.config;

import com.example.webrtc.Interceptor.AuthHandshakeInterceptor;
import com.example.webrtc.Interceptor.MyChannelInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends WebSocketMessageBrokerConfigurationSupport implements WebSocketMessageBrokerConfigurer {
    private static final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);

    @Autowired
    private AuthHandshakeInterceptor authHandshakeInterceptor;


    @Autowired
    private MyChannelInterceptor myChannelInterceptor;

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-websocket")
                .setAllowedOriginPatterns("*")
                .addInterceptors(authHandshakeInterceptor)
                .setAllowedOriginPatterns("*")
                //   .setHandshakeHandler(myHandshakeHandler)
                .withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(Integer.MAX_VALUE);
        registry.setSendBufferSizeLimit(Integer.MAX_VALUE);
        super.configureWebSocketTransport(registry);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //客户端需要把消息发送到/message/xxx地址
        registry.setApplicationDestinationPrefixes("/webSocket");
        //服务端广播消息的路径前缀，客户端需要相应订阅/topic/yyy这个地址的消息
        registry.enableSimpleBroker("/topic", "/user");
        //给指定用户发送消息的路径前缀，默认值是/user/
        registry.setUserDestinationPrefix("/user/");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(myChannelInterceptor);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        WebSocketMessageBrokerConfigurer.super.configureClientOutboundChannel(registration);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        WebSocketMessageBrokerConfigurer.super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        WebSocketMessageBrokerConfigurer.super.addReturnValueHandlers(returnValueHandlers);
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return WebSocketMessageBrokerConfigurer.super.configureMessageConverters(messageConverters);
    }

}
