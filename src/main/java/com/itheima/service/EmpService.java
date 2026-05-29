package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;

import java.util.List;

public interface EmpService extends IService<Emp> {

    /**
     * 分页查询员工信息
     * @param param 分页参数和查询条件
     * @return  分页结果
     */
    PageResult<Emp> getPageResult(EmpQueryParam param);

    /**
     * 保存员工信息
     *
     * @param emp 员工信息
     */
    void saveEmp(Emp emp);

    /**
     * 根据id查询员工信息
     * @param id    员工id
     * @return      查询到的员工信息
     */
    Emp getEmpById(Integer id);

    /**
     * 修改员工
     * @param emp 员工信息
     */
    void updateEmp(Emp emp);

    /**
     * 批量删除员工
     * @param ids   要删除的员工id
     */
    void removeEmpsByIds(List<Integer> ids);
}
