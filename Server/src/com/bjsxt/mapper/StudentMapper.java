package com.bjsxt.mapper;

import com.bjsxt.entity.Student;

import java.util.List;

public interface StudentMapper {

    //查询所有学生的操作(resultMap,association标签)
    List<Student> selectAll();

    //查询指定班级的学生
    List<Student> selectMore(int clazzno);

    //多表查询操作(resultMap,association标签)
    List<Student> selectAll2();
}
