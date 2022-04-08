package com.bjsxt.test;

import com.bjsxt.entity.VertificationCode;
import com.bjsxt.mapper.RegisterMapper;
import com.bjsxt.mapper.VertificationCodeMapper;
import com.bjsxt.util.DBUtil;
import com.bjsxt.util.Util;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class RegisterCheck implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //得到客户端传过来的实体类
        VertificationCode requestObject = (VertificationCode) Util.ArrayListToEntity(httpExchange,VertificationCode.class);
        //获得mapper
        RegisterMapper mapper = (RegisterMapper) DBUtil.getMapper(RegisterMapper.class);
        //查找对应code表找出所有与手机号相同的数据项
        List<VertificationCode> list_login_info = null;
        try {
            list_login_info = mapper.selectOne(requestObject);
        }catch (Exception e){
            e.printStackTrace();
        }
        //返回结果不为0条数据，则认为该手机号已注册
        if (list_login_info.size()!=0){
            Util.sendObject(httpExchange,"Existed");
            return;
        }else {
            System.out.println("else");
            try {
                List<VertificationCode> list_code = null;
                try {
                    list_code = mapper.selectCode(requestObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //寻找查询到的与该手机号有关的所有验证码，比较是否有与客户端输入验证码相同的验证码，并比较是否超过5分钟有效时间，
                for (VertificationCode code : list_code) {
                    if (code.getVertification_code().equals(requestObject.getVertification_code())) {
                        if ((new Date().getTime() - code.getCreate_time().getTime()) < 5 * 60 * 1000) {
                            mapper.insert(requestObject);
                            Util.sendObject(httpExchange, "Registered");
                            System.out.println(requestObject.getTelephone() + "注册成功");
                            return;
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Util.sendObject(httpExchange,"验证码错误或过期");
        System.out.println("验证码错误或过期");
    }
}
