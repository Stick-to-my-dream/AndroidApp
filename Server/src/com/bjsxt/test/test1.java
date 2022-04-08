package com.bjsxt.test;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class test1 {
    //启动后访问：http://localhost:8888/test
    public static void main(String[] args) throws Exception {
        //创建http服务器，绑定本地8888端口*
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("192.168.43.160",8000),0);
        //创建上下文监听,拦截包含/test的请求*
        httpServer.createContext("/LoginCheck", new LoginCheck());
        httpServer.createContext("/sendVertificationCode",new sendVertificationCode());
        httpServer.createContext("/RegisterCheck",new RegisterCheck());
        httpServer.start();
    }
}
