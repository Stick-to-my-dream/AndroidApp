package com.bjsxt.test;

import com.bjsxt.entity.VertificationCode;
import com.bjsxt.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

public class TestHttpHandler2 implements HttpHandler {

    @Override
    public void handle(HttpExchange  exchange) throws IOException {
        System.out.println("==================");
        VertificationCode rescode = new VertificationCode("13207502290","123456",new Date());
        VertificationCode code = (VertificationCode) Util.getRequestObject(exchange, VertificationCode.class);
        byte[] bytes = null;
        try {
            bytes = Util.objectToBytes(code);
            System.out.println(Arrays.toString(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
