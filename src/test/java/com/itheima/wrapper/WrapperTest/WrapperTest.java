package com.itheima.wrapper.WrapperTest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WrapperTest {

    @Autowired
    private EmpMapper empMapper;

    @Test
    public void testUpdateByQueryWrapper() {
        // 更新名为"李忠"的员工的薪水为9000
        Emp emp = new Emp();
        emp.setSalary(9000);
        QueryWrapper<Emp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "李忠");
        empMapper.update(emp, queryWrapper);
    }

    @Test
    public void testLambdaQueryWrapper() {
        // 查询姓名中包含“李”且薪资大于等于5000的员工的 id, name, phone, salary字段
        LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Emp::getName, "李")
                .gt(Emp::getSalary, 5000)
                .select(Emp::getId, Emp::getName, Emp::getPhone, Emp::getSalary);

        List<Emp> emps = empMapper.selectList(queryWrapper);
        System.out.println(emps);
    }

    @Test
    public void testLambdaUpdateWrapper() {
        // 更新id为5, 6, 7的员工的薪水，加2000
        LambdaUpdateWrapper<Emp> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Emp::getId, 5, 6, 7)
                .setSql("salary = salary + 2000");
        empMapper.update(updateWrapper);
    }
}
