package com.bjsxt.mapper;

import com.bjsxt.entity.VertificationCode;

import java.util.List;

public interface VertificationCodeMapper {
    int insert(VertificationCode code);
    List<VertificationCode> select(VertificationCode code);
}
