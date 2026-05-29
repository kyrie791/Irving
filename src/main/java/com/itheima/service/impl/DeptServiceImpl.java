package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.DeptMapper;
import com.itheima.pojo.Dept;
import com.itheima.service.DeptService;
import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {
//    @Autowired
//    private DeptMapper deptMapper;
//
//
//    @Override
//    public List<Dept> findAll() {
//        return deptMapper.selectList(null);
//    }
//
//    @Override
//    public void deleteById(Integer id) {
//        deptMapper.deleteById(id);
//    }
//
//    @Override
//    public void save(Dept dept) {
//        // 补全基础属性
//        dept.setCreateTime(LocalDateTime.now());
//        dept.setUpdateTime(LocalDateTime.now());
//
//        // 调用Mapper层方法保存数据
//        deptMapper.insert(dept);
//    }
//
//    @Override
//    public Dept getById(Integer id) {
//        Dept dept = deptMapper.selectById(id);
//        return dept;
//    }
//
//    @Override
//    public void update(Dept dept) {
//        // 补全基础属性
//        dept.setUpdateTime(LocalDateTime.now());
//        deptMapper.updateById(dept);
//    }
}