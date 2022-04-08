package com.bjsxt.mapper;

import com.bjsxt.entity.VertificationCode;

import java.util.List;

public interface RegisterMapper {
    int insert(VertificationCode loginInfo);
    List<VertificationCode> selectOne(VertificationCode loginInfo);
    List<VertificationCode> selectCode(VertificationCode loginInfo);
}
