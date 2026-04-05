package com.hhh.service;

import com.hhh.pojo.PageResult;
import com.hhh.pojo.Student;
import com.hhh.pojo.StudentQueryParam;

import java.util.List;

public interface StudentService {
    PageResult<Student> page(StudentQueryParam studentQueryParam);

    Student getById(Integer id);

    void add(Student student);

    void update(Student student);

    void delete(List<Integer> ids);

    void addViolation(Integer id, Integer score);
}
