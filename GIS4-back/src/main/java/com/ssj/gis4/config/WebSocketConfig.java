package com.ssj.gis4.config;

import com.ssj.gis4.mapper.NodeMapper;
import com.ssj.gis4.service.DataPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.ssj.gis4.handler.MyWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private DataPushService dataPushService;

    @Autowired
    private MyWebSocketHandler myWebSocketHandler; // 注入你的WebSocketHandler

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler, "/websocket").setAllowedOrigins("*");
    }

//    @Bean
//    public MyWebSocketHandler myHandler() {
//
//        MyWebSocketHandler handler = new MyWebSocketHandler();
//        handler.setNodeMapper(nodeMapper); // 假设你添加了相应的setter方法
//        handler.setDataPushService(dataPushService); // 假设你添加了相应的setter方法
//
//        return handler;
//
//    }

}