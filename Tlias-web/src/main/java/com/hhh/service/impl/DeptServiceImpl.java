package com.hhh.service.impl;

import com.hhh.mapper.DeptMapper;
import com.hhh.mapper.EmpMapper;
import com.hhh.pojo.Dept;
import com.hhh.service.DeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {
    private final DeptMapper deptMapper;
    private final EmpMapper empMapper;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        Dept dept = deptMapper.getById(id);
        if (dept == null) {
            throw new IllegalArgumentException("部门不存在");
        }
        Integer employeeCount = empMapper.countByDeptId(id);
        if (employeeCount != null && employeeCount > 0) {
            throw new IllegalStateException("当前部门下存在员工，无法删除");
        }
        deptMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Dept dept) {
        LocalDateTime now = LocalDateTime.now();
        dept.setCreateTime(now);
        dept.setUpdateTime(now);
        deptMapper.insert(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Dept dept) {
        Dept dbDept = deptMapper.getById(dept.getId());
        if (dbDept == null) {
            throw new IllegalArgumentException("部门不存在");
        }
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }
}
