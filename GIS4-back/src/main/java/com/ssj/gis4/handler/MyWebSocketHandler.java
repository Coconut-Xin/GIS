package com.ssj.gis4.handler;

import com.ssj.gis4.mapper.NodeMapper;
import com.ssj.gis4.service.DataPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyWebSocketHandler  extends  TextWebSocketHandler {

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private DataPushService dataPushService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        System.out.println("Connection established");


        dataPushService.registerSession(session); // 注册session

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message: " + message.getPayload());
        dataPushService.registerSession(session);
//        session.sendMessage(new TextMessage("Hello from server!"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Connection closed");

        dataPushService.unregisterSession(session); // 注销session
    }

    public void setNodeMapper(NodeMapper nodeMapper) {
        this.nodeMapper = nodeMapper;
    }

    public void setDataPushService(DataPushService dataPushService) {
        this.dataPushService = dataPushService;
    }

}