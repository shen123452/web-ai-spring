package com.hhh.controller;

import com.hhh.pojo.Emp;
import com.hhh.pojo.EmpQueryParam;
import com.hhh.pojo.PageResult;
import com.hhh.pojo.Result;
import com.hhh.service.EmpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emps")
@RequiredArgsConstructor
public class EmpController {
    private final EmpService empService;

    @GetMapping
    public Result<PageResult<Emp>> page(EmpQueryParam empQueryParam) {
        log.info("Page query employees: {}", empQueryParam);
        return Result.success(empService.page(empQueryParam));
    }

    @GetMapping("/list")
    public Result<List<Emp>> list() {
        log.info("Query simple employee list");
        return Result.success(empService.list());
    }

    @GetMapping("/{id}")
    public Result<Emp> getById(@PathVariable Integer id) {
        log.info("Query employee by id: {}", id);
        return Result.success(empService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Emp emp) {
        log.info("Add employee: {}", emp);
        empService.add(emp);
        return Result.success();
    }

    @PutMapping({"", "/{id}"})
    public Result<Void> update(
            @PathVariable(required = false) Integer id,
            @RequestBody Emp emp
    ) {
        if (id != null) {
            emp.setId(id);
        }
        if (emp.getId() == null) {
            throw new IllegalArgumentException("员工ID不能为空");
        }
        log.info("Update employee: {}", emp);
        empService.update(emp);
        return Result.success();
    }

    @DeleteMapping({"", "/{ids}"})
    public Result<Void> delete(
            @PathVariable(required = false) String ids,
            @RequestParam(name = "id", required = false) String idParam,
            @RequestParam(name = "ids", required = false) String idsParam
    ) {
        String rawIds = StringUtils.hasText(ids) ? ids : (StringUtils.hasText(idsParam) ? idsParam : idParam);
        if (!StringUtils.hasText(rawIds)) {
            throw new IllegalArgumentException("员工ID不能为空");
        }

        List<Integer> idList = Arrays.stream(rawIds.split(","))
                .map(String::trim)
                .filter(StringUtils::hasText)
                .map(Integer::valueOf)
                .toList();
        log.info("Delete employees: {}", idList);
        empService.delete(idList);
        return Result.success();
    }
}
