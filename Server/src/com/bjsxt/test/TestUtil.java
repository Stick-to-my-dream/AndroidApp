package com.bjsxt.test;

import com.bjsxt.entity.VertificationCode;
import com.bjsxt.mapper.VertificationCodeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class TestUtil {
    public static void main(String[] args) throws IOException {
        System.out.println(new Date().getTime());
//        List<VertificationCode> list = mapper.select();
//        System.out.println(list.get(1).getCreate_time().toLocaleString());
    }
}
