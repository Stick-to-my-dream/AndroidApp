package com.bjsxt.test;

import com.bjsxt.entity.Clazz;
import com.bjsxt.entity.Student;
import com.bjsxt.mapper.ClazzMapper;
import com.bjsxt.mapper.StudentMapper;
import com.bjsxt.util.DBUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/*
* 多表查询的操作
* */
public class TestD {
    public static void main(String[] args) throws IOException {
        //利用ThreadLocal可以保证对于同一个线程不会重复获取sqlsession对象(获取时需要连接数据库)，
        // 避免一个线程频繁连接数据库
        new TestD().selectAll();
    }

    public void selectAll(){
        SqlSession sqlSession = DBUtil.getSqlSession();
        // 利用sqlsession执行操作
        System.out.println("TestD: "+sqlSession);
        new TestF().selectAll();
    }
}
