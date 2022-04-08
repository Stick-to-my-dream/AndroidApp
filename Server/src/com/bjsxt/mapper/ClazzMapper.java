package com.bjsxt.mapper;

import com.bjsxt.entity.Clazz;

import java.util.List;

public interface ClazzMapper {

    //查询指定学生所在班级的信息(resultMap,association标签)
    Clazz selectOne(int clazzno);

    //查询所有班级信息
    List<Clazz> selectAll();

    //多表查询操作
    List<Clazz> selectAll2();
}
