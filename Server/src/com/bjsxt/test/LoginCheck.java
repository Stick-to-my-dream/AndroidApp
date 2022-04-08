package com.bjsxt.test;

import com.bjsxt.entity.VertificationCode;
import com.bjsxt.mapper.RegisterMapper;
import com.bjsxt.mapper.VertificationCodeMapper;
import com.bjsxt.util.DBUtil;
import com.bjsxt.util.Util;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class LoginCheck implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getResponseBody()+"...");
        //getRequestObject返回的数据类型取决于客户端的封装，
        //客户端的传输是把类转换成字节数组，再将字节数组转成json
//        Gson gson = new Gson();
//        byte[] bytes=null;
//        try {
//            bytes = Tools.objectToBytes(obj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String json = gson.toJson(bytes);
//        RequestBody body = RequestBody.create(
//                json,
//                MediaType.parse("application/json; charset=utf-8")
//        );
        //如果直接将类转换成json，会将类中的属性转换成LinkedHashMap的键值对

        //得到客户端传过来的实体类
        VertificationCode requestObject = (VertificationCode) Util.ArrayListToEntity(exchange,VertificationCode.class);
        //获得mapper
        RegisterMapper mapper = (RegisterMapper) DBUtil.getMapper(RegisterMapper.class);
        //查找对应code表找出所有与手机号相同的数据项
        List<VertificationCode> list1 = null;
        try {
            list1 = mapper.selectOne(requestObject);
        }catch (Exception e){
            e.printStackTrace();
        }
        //当检索数据库的code表(本来应该检索account表，来判断，只不过现在没有使用该表)时，返回结果为0条数据，则认为该手机号未注册
        if (list1.size()==0){
            Util.sendObject(exchange,"NoUser");
            return;
        }
        //寻找查询到的与该手机号有关的所有验证码，比较是否有与客户端输入验证码相同的验证码，并比较是否超过5分钟有效时间，
        List<VertificationCode> list2 = null;
        try {
            list2 = mapper.selectCode(requestObject);
        }catch (Exception e){
            e.printStackTrace();
        }
        for(VertificationCode code:list2){
            if(code.getVertification_code().equals(requestObject.getVertification_code())){
                if((new Date().getTime()-code.getCreate_time().getTime())<5*60*1000){
                    Util.sendObject(exchange,"login");
                    System.out.println(requestObject.getTelephone()+"登录成功");
                    return;
                }
            }
        }
        Util.sendObject(exchange,"验证码错误或过期");
        System.out.println("验证码错误或过期");

    }
}
