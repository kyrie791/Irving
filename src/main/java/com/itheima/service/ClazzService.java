package com.itheima.service;

import com.itheima.pojo.Clazz;
import java.util.List;

public interface ClazzService {
    // 查询所有班级
    List<Clazz> findAll();
}