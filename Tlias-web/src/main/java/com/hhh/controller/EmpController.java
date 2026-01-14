package com.hhh.controller;

import com.hhh.pojo.Emp;
import com.hhh.pojo.PageResult;
import com.hhh.pojo.Result;
import com.hhh.service.EmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpController {
    private static final Logger log = LoggerFactory.getLogger(EmpController.class);

    @Autowired
    private EmpService empService;

    @GetMapping("/emps")
    public Result<PageResult<Emp>> page(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize
    ) {
        log.info("分页查询员工: page={}, pageSize={}", page, pageSize);
        return Result.success(empService.page(page, pageSize));
    }

    @PostMapping("/emps")
    public Result add(@RequestBody Emp emp) {
        log.info("新增员工: {}", emp);
        empService.add(emp);
        return Result.success();
    }

    @PutMapping("/emps/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Emp emp) {
        emp.setId(id);
        log.info("更新员工: {}", emp);
        empService.update(emp);
        return Result.success();
    }

    @DeleteMapping("/emps/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除员工: {}", id);
        empService.delete(id);
        return Result.success();
    }
}
