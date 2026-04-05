package com.hhh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hhh.mapper.StudentMapper;
import com.hhh.pojo.EmpLog;
import com.hhh.pojo.PageResult;
import com.hhh.pojo.Student;
import com.hhh.pojo.StudentQueryParam;
import com.hhh.service.EmpLogService;
import com.hhh.service.StudentService;
import com.hhh.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;
    private final EmpLogService empLogService;

    @Override
    public PageResult<Student> page(StudentQueryParam studentQueryParam) {
        PageHelper.startPage(studentQueryParam.getPage(), studentQueryParam.getPageSize());
        Page<Student> studentPage = (Page<Student>) studentMapper.list(studentQueryParam);
        return new PageResult<>(studentPage.getTotal(), studentPage.getResult());
    }

    @Override
    public Student getById(Integer id) {
        return studentMapper.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Student student) {
        LocalDateTime now = LocalDateTime.now();
        student.setCreateTime(now);
        student.setUpdateTime(now);
        student.setViolationCount(student.getViolationCount() == null ? 0 : student.getViolationCount());
        student.setViolationScore(student.getViolationScore() == null ? 0 : student.getViolationScore());
        studentMapper.insert(student);
        writeLog(now, "ADD_STUDENT", student.getName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Student student) {
        Student dbStudent = studentMapper.getById(student.getId());
        if (dbStudent == null) {
            throw new IllegalArgumentException("学员不存在");
        }
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
        writeLog(LocalDateTime.now(), "UPDATE_STUDENT", String.valueOf(student.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (Integer id : ids) {
            Student student = studentMapper.getById(id);
            if (student == null) {
                throw new IllegalArgumentException("学员不存在");
            }
            studentMapper.deleteById(id);
        }
        writeLog(LocalDateTime.now(), "DELETE_STUDENT", ids.toString());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addViolation(Integer id, Integer score) {
        Student student = studentMapper.getById(id);
        if (student == null) {
            throw new IllegalArgumentException("学员不存在");
        }
        studentMapper.addViolation(id, score);
        writeLog(LocalDateTime.now(), "VIOLATION_STUDENT", "id=" + id + ",score=" + score);
    }

    private void writeLog(LocalDateTime time, String action, String detail) {
        String info = "operator=" + UserContext.getUsernameOrDefault("system")
                + ";action=" + action
                + ";detail=" + detail;
        empLogService.insertLog(new EmpLog(null, time, info));
    }
}
