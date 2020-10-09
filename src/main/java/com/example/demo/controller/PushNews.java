package com.example.demo.controller;

import com.example.demo.server.WebsocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PushNews {

    @Resource
    private  WebsocketServer websocketServer;



    private static int a;

    static {
      a=1;
    }

    @Scheduled(fixedDelay = 3000)
    public void pushNews() throws IOException {
        ConcurrentHashMap<String, Session> pushMap = WebsocketServer.getMap();
        if (pushMap.size()==0 && pushMap==null){
            System.out.println("暂时还没有人连接进来");
        } else {
            for (Map.Entry<String, Session> entry : pushMap.entrySet()) {
                Session session = entry.getValue();
                websocketServer.sendMsg(session, "服务器推送的新闻：今晚是新闻30分" + a++);
            }
        }
    }

}
