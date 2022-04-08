package com.bjsxt.test;

import com.bjsxt.entity.Clazz;
import com.bjsxt.entity.Student;
import com.bjsxt.mapper.ClazzMapper;
import com.bjsxt.mapper.StudentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Struct;
import java.util.List;

/*
* 多表查询的操作
* */
public class TestC {
    public static void main(String[] args) throws IOException {
        //[A] 解析mybatis.xml文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        //[B] 获得session工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        //[C] 获得session对象
        SqlSession sqlSession = factory.openSession(true);
        //[D] 执行方法
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        ClazzMapper clazzMapper = sqlSession.getMapper(ClazzMapper.class);

        //查询所有学生的班级信息
        List<Student> list = studentMapper.selectAll2();

        for(Student stu:list){
            System.out.println(stu);
        }

        //[F] 关闭资源
        sqlSession.close();
    }
}
