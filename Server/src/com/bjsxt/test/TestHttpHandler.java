package com.bjsxt.test;

import com.bjsxt.entity.VertificationCode;
import com.bjsxt.mapper.VertificationCodeMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestHttpHandler implements HttpHandler {

    private byte[] bytes;

    @Override
    public void handle(HttpExchange exchange) throws IOException {


        //[A] 解析mybatis.xml文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        //[B] 获得session工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        //[C] 获得session对象
        SqlSession sqlSession = factory.openSession(true);
        //[D] 执行方法
        VertificationCode code = new VertificationCode("13207502290","123456",new Date());
        VertificationCodeMapper mapper = sqlSession.getMapper(VertificationCodeMapper.class);
        mapper.insert(code);
////        List<VertificationCode> list = mapper.select();
////        System.out.println(list.get(1).getCreate_time().toLocaleString());
//
//        try {
//            bytes = objectToBytes(list.get(1));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        exchange.sendResponseHeaders(200, 0);
//        OutputStream os = exchange.getResponseBody();
////        os.write(response.getBytes("UTF-8"));
////        os.write(response.getBytes(StandardCharsets.UTF_8));
//        System.out.println(Arrays.toString(bytes));
//        os.write(bytes);
//        os.close();
    }

    /**
     * 对象转Byte数组
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static byte[] objectToBytes(Object obj) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream sOut = new ObjectOutputStream(out);
        sOut.writeObject(obj);
        sOut.flush();
        byte[] bytes = out.toByteArray();


        return bytes;
    }


    public static Object bytesToObject(byte[] bytes) throws Exception {

//byte转object
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn = new ObjectInputStream(in);
        return sIn.readObject();
    }

}
