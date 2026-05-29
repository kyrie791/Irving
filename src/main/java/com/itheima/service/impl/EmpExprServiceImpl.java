package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.pojo.EmpExpr;
import com.itheima.service.EmpExprService;
import org.springframework.stereotype.Service;

@Service
public class EmpExprServiceImpl extends ServiceImpl<EmpExprMapper, EmpExpr> implements EmpExprService {
}
