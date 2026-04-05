package com.hhh.controller;

import com.hhh.pojo.Clazz;
import com.hhh.pojo.ClazzQueryParam;
import com.hhh.pojo.PageResult;
import com.hhh.pojo.Result;
import com.hhh.service.ClazzService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clazzs")
@RequiredArgsConstructor
public class ClazzController {
    private final ClazzService clazzService;

    @GetMapping
    public Result<PageResult<Clazz>> page(ClazzQueryParam clazzQueryParam) {
        log.info("Page query clazzs: {}", clazzQueryParam);
        return Result.success(clazzService.page(clazzQueryParam));
    }

    @GetMapping("/list")
    public Result<List<Clazz>> list() {
        log.info("Query clazz list");
        return Result.success(clazzService.list());
    }

    @GetMapping("/{id}")
    public Result<Clazz> getById(@PathVariable Integer id) {
        log.info("Query clazz by id: {}", id);
        return Result.success(clazzService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Clazz clazz) {
        log.info("Add clazz: {}", clazz);
        clazzService.add(clazz);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Clazz clazz) {
        if (clazz.getId() == null) {
            throw new IllegalArgumentException("班级ID不能为空");
        }
        log.info("Update clazz: {}", clazz);
        clazzService.update(clazz);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        log.info("Delete clazz: {}", id);
        clazzService.delete(id);
        return Result.success();
    }
}
