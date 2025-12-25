package com.hhh.controller;

import com.hhh.pojo.Dept;
import com.hhh.pojo.Result;
import com.hhh.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {
    private static final Logger log = LoggerFactory.getLogger(DeptController.class);

    // 注入DeptService
    @Autowired
    private DeptService deptService;

    @GetMapping("/depts")
    public Result<List<Dept>> list(){
        log.info("查询所有部门");
        // 调用service查询所有部门,并返回结果
        return Result.success(deptService.list());
    }

    @GetMapping("/depts/{id}")
    public Result<Dept> getById(@PathVariable Integer id) {
        log.info("根据id查询部门: {}", id);
        return Result.success(deptService.getById(id));
    }
//    @DeleteMapping("/depts/{id}")
//    public Result delete(@PathVariable Integer id){
//        System.out.println("根据id删除部门"+id);
//        // 调用service删除部门
//        deptService.delete(id);
//        // 返回结果
//        return Result.success(deptService.list());
//    }

    @DeleteMapping("/depts")
    public Result deleteByParam(@RequestParam Integer id){
        log.info("根据id删除部门(参数): {}", id);
        deptService.delete(id);
        return Result.success(deptService.list());
    }

    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept) {
        log.info("新增部门: {}", dept);
        deptService.add(dept);
        return Result.success();
    }

    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept) {
        log.info("更新部门: {}", dept);
        deptService.update(dept);
        return Result.success();
    }

    @PutMapping("/depts/{id}")
    public Result updateByPath(@PathVariable Integer id, @RequestBody Dept dept) {
        dept.setId(id);
        log.info("更新部门(路径): {}", dept);
        deptService.update(dept);
        return Result.success();
    }

}
