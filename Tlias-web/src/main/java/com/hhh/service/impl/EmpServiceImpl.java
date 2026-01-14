package com.hhh.service.impl;

import com.hhh.maper.EmpExprMapper;
import com.hhh.maper.EmpMapper;
import com.hhh.pojo.Emp;
import com.hhh.pojo.EmpExpr;
import com.hhh.pojo.PageResult;
import com.hhh.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    private static final String DEFAULT_PASSWORD = "123456";

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        Integer pageNo = page == null || page < 1 ? 1 : page;
        Integer size = pageSize == null || pageSize < 1 ? 10 : pageSize;
        Integer offset = (pageNo - 1) * size;
        Long total = empMapper.count();
        List<Emp> rows = empMapper.list(offset, size);
        return new PageResult<>(total, rows);
    }

    @Override
    @Transactional
    public void add(Emp emp) {
        LocalDateTime now = LocalDateTime.now();
        emp.setCreateTime(now);
        emp.setUpdateTime(now);
        if (emp.getPassword() == null || emp.getPassword().isBlank()) {
            emp.setPassword(DEFAULT_PASSWORD);
        }
        empMapper.insert(emp);
        insertExprs(emp.getId(), emp.getExprs());
    }

    @Override
    @Transactional
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
        if (emp.getExprs() != null) {
            empExprMapper.deleteByEmpId(emp.getId());
            insertExprs(emp.getId(), emp.getExprs());
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        empExprMapper.deleteByEmpId(id);
        empMapper.deleteById(id);
    }

    private void insertExprs(Integer empId, List<EmpExpr> exprs) {
        if (exprs == null || exprs.isEmpty()) {
            return;
        }
        for (EmpExpr expr : exprs) {
            expr.setEmpId(empId);
        }
        empExprMapper.insertBatch(exprs);
    }
}
