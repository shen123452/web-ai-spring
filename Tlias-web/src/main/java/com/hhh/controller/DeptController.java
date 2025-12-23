package com.hhh.controller;

import com.hhh.pojo.Dept;
import com.hhh.pojo.Result;
import com.hhh.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {
    // 注入DeptService
    @Autowired
    private DeptService deptService;

    @GetMapping("/depts")
    public Result<List<Dept>> list(){
        System.out.println("查询所有部门");
        // 调用service查询所有部门,并返回结果
        return Result.success(deptService.list());
    }

}
