//package com.ssj.gis4.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//public class WebSocketController {
//
//
//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public String greeting(String message) {
//        return "Hello, " + message + "!";
//    }
//
//}