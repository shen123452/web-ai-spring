package com.hhh.controller;

import com.hhh.pojo.PageResult;
import com.hhh.pojo.Result;
import com.hhh.pojo.Student;
import com.hhh.pojo.StudentQueryParam;
import com.hhh.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public Result<PageResult<Student>> page(StudentQueryParam studentQueryParam) {
        log.info("Page query students: {}", studentQueryParam);
        return Result.success(studentService.page(studentQueryParam));
    }

    @GetMapping("/{id}")
    public Result<Student> getById(@PathVariable Integer id) {
        log.info("Query student by id: {}", id);
        return Result.success(studentService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Student student) {
        log.info("Add student: {}", student);
        studentService.add(student);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Student student) {
        if (student.getId() == null) {
            throw new IllegalArgumentException("学员ID不能为空");
        }
        log.info("Update student: {}", student);
        studentService.update(student);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result<Void> delete(@PathVariable String ids) {
        if (!StringUtils.hasText(ids)) {
            throw new IllegalArgumentException("学员ID不能为空");
        }
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .filter(StringUtils::hasText)
                .map(Integer::valueOf)
                .toList();
        log.info("Delete students: {}", idList);
        studentService.delete(idList);
        return Result.success();
    }

    @PutMapping("/violation/{id}/{score}")
    public Result<Void> addViolation(@PathVariable Integer id, @PathVariable Integer score) {
        if (score == null || score < 0) {
            throw new IllegalArgumentException("违纪扣分必须大于等于0");
        }
        log.info("Student violation, id: {}, score: {}", id, score);
        studentService.addViolation(id, score);
        return Result.success();
    }
}
