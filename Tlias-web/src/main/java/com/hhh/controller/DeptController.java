package com.hhh.controller;

import com.hhh.pojo.Dept;
import com.hhh.pojo.Result;
import com.hhh.service.DeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/depts")
@RequiredArgsConstructor
public class DeptController {
    private final DeptService deptService;

    @GetMapping
    public Result<List<Dept>> list() {
        log.info("Query department list");
        return Result.success(deptService.list());
    }

    @GetMapping("/{id}")
    public Result<Dept> getById(@PathVariable Integer id) {
        log.info("Query department by id: {}", id);
        return Result.success(deptService.getById(id));
    }

    @DeleteMapping({"", "/{id}"})
    public Result<Void> delete(
            @PathVariable(required = false) Integer id,
            @RequestParam(name = "id", required = false) Integer requestId
    ) {
        Integer deptId = id != null ? id : requestId;
        if (deptId == null) {
            throw new IllegalArgumentException("部门ID不能为空");
        }
        log.info("Delete department: {}", deptId);
        deptService.delete(deptId);
        return Result.success();
    }

    @PostMapping
    public Result<Void> add(@RequestBody Dept dept) {
        log.info("Add department: {}", dept);
        deptService.add(dept);
        return Result.success();
    }

    @PutMapping({"", "/{id}"})
    public Result<Void> update(
            @PathVariable(required = false) Integer id,
            @RequestBody Dept dept
    ) {
        if (id != null) {
            dept.setId(id);
        }
        if (dept.getId() == null) {
            throw new IllegalArgumentException("部门ID不能为空");
        }
        log.info("Update department: {}", dept);
        deptService.update(dept);
        return Result.success();
    }
}
