package com.example.demo.server;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/hehe/{name}")
public class WebsocketServer {



    private static final ConcurrentHashMap<String,Session> map = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Session> getMap() {
        return map;
    }

    @OnOpen
    public void onOpen( @PathParam("name") String name, Session session){
        map.put(name, session);
        System.out.println(name+"进来了");
    }

    @OnClose
    public void onClose(@PathParam("name") String name){
        System.out.println(name+"断开连接了");
    }

    @OnMessage
    public void onMessage(String msg,@PathParam("name") String name) throws IOException {
        System.out.println("收到"+name+"  发的消息，内容是："+msg);
        if(msg.equals("我出去打工了")){
            Session wife = map.get("老婆");
            sendMsg(wife,"你丈夫出去打工去了，叫你改嫁了");
        }
    }

    public void sendMsg(Session session,String msg) throws IOException {
         session.getBasicRemote().sendText(msg);
    }

}
