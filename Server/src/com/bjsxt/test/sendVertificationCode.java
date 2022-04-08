package com.bjsxt.test;

import com.bjsxt.entity.VertificationCode;
import com.bjsxt.mapper.VertificationCodeMapper;
import com.bjsxt.util.DBUtil;
import com.bjsxt.util.Util;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;


public class sendVertificationCode implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //生成随机验证码
        String code = Util.getVertificationCode();
        //获取客户端发来的手机号
        VertificationCode vcode = (VertificationCode) Util.ArrayListToEntity(httpExchange,VertificationCode.class);
        //将手机号，验证码，生成时间存入数据库
        VertificationCode responseCode = new VertificationCode(vcode.getTelephone(),code,new Date());
        VertificationCodeMapper mapper = (VertificationCodeMapper) DBUtil.getMapper(VertificationCodeMapper.class);
        mapper.insert(responseCode);
        //将验证码发给客户端
        Util.sendObject(httpExchange,responseCode);
    }

}
