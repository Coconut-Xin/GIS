package com.ssj.gis4.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssj.gis4.domain.Node;
import com.ssj.gis4.handler.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
//import com.fasterxml.jackson.databind.ObjectMapper;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * ClassName: DataPushService
 * Packge:
 * Description:
 *
 * @Author:孙世杰
 * @Create 2024/6/27 17:27
 * Version 1.0
 */

public interface DataPushService {


    void registerSession(WebSocketSession session);

    void unregisterSession(WebSocketSession session);
}
