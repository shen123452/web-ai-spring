package com.hhh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hhh.maper.EmpExprMapper;
import com.hhh.maper.EmpMapper;
import com.hhh.pojo.Emp;
import com.hhh.pojo.EmpExpr;
import com.hhh.pojo.EmpQueryParam;
import com.hhh.pojo.PageResult;
import com.hhh.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    private static final String DEFAULT_PASSWORD = "123456";

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Override // 分页查询员工列表
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //设置分页参数
        PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());
        //查询员工列表
        Page<Emp> empPage = (Page<Emp>) empMapper.list(empQueryParam);//将获得的员工列表数据封装到Page对象中（强转）
        //返回分页结果
        return new PageResult<>(empPage.getTotal(), empPage.getResult());
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
