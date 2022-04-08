package com.bjsxt.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class DBUtil {
    private static SqlSessionFactory factory = null;
    //保证对于同一个线程，不重复获取sqlsession
    //ThreadLocal类似map.put("线程的id",sqlsession)
    private static ThreadLocal<SqlSession> t1 = new ThreadLocal<>();

    static {
        //[A] 解析mybatis.xml文件
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis.xml");
            //[B] 获得session工厂
            factory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getMapper(Class clazz){
        System.out.println("获取"+clazz.getName()+"mapper");
        return getSqlSession().getMapper(clazz);
    }

    public static SqlSession getSqlSession(){
        SqlSession sqlSession = t1.get();
        if(sqlSession==null){
            sqlSession=factory.openSession(true);
            t1.set(sqlSession);
        }
        return t1.get();
    }

    // 关闭sqlsession
    public static void closeAll(){
        SqlSession sqlSession = t1.get();
        if(sqlSession!=null){
            sqlSession.close();
        }
        t1.set(null);
    }


}
