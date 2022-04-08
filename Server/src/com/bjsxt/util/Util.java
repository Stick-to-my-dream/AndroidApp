package com.bjsxt.util;

import com.bjsxt.entity.VertificationCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

public class Util {
    //根据时间戳获取10位随机数
    public static String getAccountNum(){
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        String ma="yyyyMMddHHmmss";
        SimpleDateFormat forma=new SimpleDateFormat(ma);
        String nwdate=forma.format(date);
//        System.out.println(nwdate);
        return nwdate;

    }

    /**
     * 发送字符串给客户端
     * @param exchange
     * @param str 要返回给客户端的字符串
     * @throws IOException
     */
    public static void sendString(HttpExchange exchange,String str) throws IOException {
        //返回给客户端字符串提示
        exchange.sendResponseHeaders(200, 0);
        OutputStream os = exchange.getResponseBody();
        os.write(str.getBytes("UTF-8"));
    }

    /**
     * 发送类给客户端
     * @param exchange
     * @param send 要发送的类
     * @throws IOException
     */
    public static void sendObject(HttpExchange exchange,Object send) throws IOException {
        exchange.sendResponseHeaders(200, 0);
        OutputStream os = exchange.getResponseBody();
        byte[] bytes1 = null;
        try {
            bytes1 = objectToBytes(send);
        } catch (Exception e) {
            e.printStackTrace();
        }
        os.write(bytes1);
        os.close();
    }

    /**
     * 负责把客户端的json数据转换成ArrayList,因为客户端封装后会将实体类序列化后的字节数组变成ArrayList
     * @param exchange
     * @param jsonClass
     * @return
     * @throws IOException
     */
    public static Object getRequestObject(HttpExchange exchange, Class jsonClass) throws IOException {
        Object obj = null;
        //解析客户端的json数据
        ObjectMapper mapper = new ObjectMapper();
        InputStream requestBody = exchange.getRequestBody();

        byte[] bytes = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(requestBody);
        int n = bis.read(bytes);

        if(n!=-1){
            obj = mapper.readValue(bytes,Object.class); //会把类解析成键值对
        }
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+".getRequestObject:"+"成功解析客户端json数据！");
        bis.close();

        return obj;
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


    /**
     * 字节数组转对象
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object bytesToObject(byte[] bytes) throws Exception {

//byte转object
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn = new ObjectInputStream(in);
        return sIn.readObject();

    }

    /**
     * 生成随机验证码
     * @return
     */
    public static String getVertificationCode(){
        String code = String.valueOf((int)(Math.random()*100000+100000));
        return code;
    }

    /**
     * 将list中的值变成字节数组，用于将其还原为类
     * @param list 客户端将类转换成字节数组封装进json后以ArrayList<Integer>类型发过来的
     * @return
     */
    public static byte[] ArrayListToBytes(ArrayList list){
        try {
            byte[] bytes = new byte[1024];
            for (int i = 0; i < list.size(); i++) {
                bytes[i] = (byte) ((Integer)list.get(i)).intValue();
            }
            return bytes;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将ArrayList转换成字节数组，并转换为实体类返回
     * @param exchange
     * @param clazz
     * @return
     */
    public static Object ArrayListToEntity(HttpExchange exchange,Class clazz){
        ArrayList list = null;
        try {
            list = (ArrayList) Util.getRequestObject(exchange, VertificationCode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        byte[] bytes = Util.ArrayListToBytes(list);
        Object requestObject=null;
        try {
            requestObject = Util.bytesToObject(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestObject;
    }
}
