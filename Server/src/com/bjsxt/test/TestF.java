package com.bjsxt.test;

import com.bjsxt.util.DBUtil;
import org.apache.ibatis.session.SqlSession;

public class TestF {
    public void selectAll(){
        SqlSession sqlSession = DBUtil.getSqlSession();
        //利用sqlsession执行操作
        System.out.println("TestF:"+sqlSession);
    }
}
