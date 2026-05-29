package com.itheima.controller;

import com.itheima.pojo.Clazz;
import com.itheima.pojo.Result;
import com.itheima.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/clazzs")
@RestController
public class ClazzController {

    // 注入 Service
    @Autowired
    private ClazzService clazzService;

    // 主列表查询（分页 + 条件）
    @GetMapping
    public Result list(String name,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                       @RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int pageSize) {
        log.info("分页查询班级列表：name={}, begin={}, end={}, page={}, pageSize={}",
                name, begin, end, page, pageSize);

        // 真正调用 Service 查询全部班级（后续你可以改成分页查询）
        List<Clazz> clazzList = clazzService.findAll();

        Map<String, Object> data = new HashMap<>();
        data.put("rows", clazzList);  // 返回真实数据
        data.put("total", (long) clazzList.size()); // 真实总数
        return Result.success(data);
    }

    // 查询全部班级（下拉框/无分页）
    @GetMapping("/list")
    public Result listAll() {
        log.info("查询全部班级");
        List<Clazz> clazzList = clazzService.findAll();
        return Result.success(clazzList);
    }

    // 新增
    @PostMapping
    public Result save(@RequestBody Clazz clazz) {
        log.info("新增班级：{}", clazz);
        return Result.success();
    }

    // 根据ID查询
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据id查询班级：{}", id);
        return Result.success();
    }

    // 修改
    @PutMapping
    public Result update(@RequestBody Clazz clazz) {
        log.info("修改班级：{}", clazz);
        return Result.success();
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除班级：{}", id);
        return Result.success();
    }
}