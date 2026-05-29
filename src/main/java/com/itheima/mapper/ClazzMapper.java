package com.itheima.mapper;

import com.itheima.pojo.Clazz;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ClazzMapper {
    // 查询所有班级
    List<Clazz> findAll();
}