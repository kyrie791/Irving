package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.*;
import com.itheima.service.DeptService;
import com.itheima.service.EmpExprService;
import com.itheima.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements EmpService {
    @Autowired
    private DeptService deptService;

    // ✅ 在这里加入员工经历服务
    @Autowired
    private EmpExprService exprService;

    /**
     * 分页查询
     */
    @Override
    public PageResult<Emp> getPageResult(EmpQueryParam param) {
        Page<Emp> page = Page.of(param.getPage(), param.getPageSize());
        page.addOrder(OrderItem.desc("update_time"));

        // 分页条件查询
        page = lambdaQuery()
                .like(param.getName() != null && !param.getName().isEmpty(), Emp::getName, param.getName())
                .eq(param.getGender() != null, Emp::getGender, param.getGender())
                .between(param.getBegin() != null && param.getEnd() != null, Emp::getEntryDate, param.getBegin(), param.getEnd())
                .page(page);

        List<Emp> emps = page.getRecords();

        // 修复核心：过滤 null 的 deptId
        List<Integer> deptIds = emps.stream()
                .map(Emp::getDeptId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // 根据部门ID批量查询部门
        if (!deptIds.isEmpty()) {
            List<Dept> depts = deptService.listByIds(deptIds);
            // 修复类型不匹配：Long → Integer
            Map<Integer, String> deptMap = depts.stream()
                    .collect(Collectors.toMap(
                            dept -> dept.getId().intValue(),
                            Dept::getName
                    ));

            // 设置部门名称
            emps.forEach(emp -> {
                if (emp.getDeptId() != null) {
                    emp.setDeptName(deptMap.get(emp.getDeptId()));
                }
            });
        }

        return new PageResult<>(page.getTotal(), emps);
    }

    /**
     * 保存员工信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveEmp(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());

        save(emp);

        List<EmpExpr> exprList = emp.getExprList();

        if (exprList != null && !exprList.isEmpty()) {
            exprList.forEach(expr -> {
                expr.setEmpId(emp.getId());
            });
            // 现在可以正常使用了
            exprService.saveBatch(exprList);
        }
    }

    /**
     * 根据id查询员工信息
     *
     * @param id 员工id
     * @return 查询到的员工信息
     */
    @Override
    public Emp getEmpById(Integer id) {
        Emp emp = getById(id);

        // 工作经历列表
        emp.setExprList(exprService.list(new LambdaQueryWrapper<EmpExpr>().eq(EmpExpr::getEmpId, id)));
        return emp;
    }

    /**
     * 修改员工
     *
     * @param emp 员工信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmp(Emp emp) {
        // 补全更新时间
        emp.setUpdateTime(LocalDateTime.now());

        // 1. 更新基本信息
        updateById(emp);

        // 2. 删除原有工作经历
        LambdaUpdateWrapper<EmpExpr> deleteWrapper = new LambdaUpdateWrapper<>();
        deleteWrapper.eq(EmpExpr::getEmpId, emp.getId());
        exprService.remove(deleteWrapper);

        // 3. 新增新的工作经历
        List<EmpExpr> exprList = emp.getExprList();
        if (exprList != null && !exprList.isEmpty()) {
            exprList.forEach(expr -> expr.setEmpId(emp.getId()));
            exprService.saveBatch(exprList);
        }
    }

    /**
     * 批量删除员工
     *
     * @param ids 要删除的员工id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeEmpsByIds(List<Integer> ids) {
        // 调用service层方法批量删除员工信息
        removeBatchByIds(ids);

        // 删除员工工作经历信息
        exprService.remove(Wrappers.<EmpExpr>lambdaQuery().in(EmpExpr::getEmpId, ids));
    }
}