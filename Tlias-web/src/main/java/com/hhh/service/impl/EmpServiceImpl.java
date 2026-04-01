package com.hhh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hhh.mapper.EmpExprMapper;
import com.hhh.mapper.EmpMapper;
import com.hhh.pojo.*;
import com.hhh.service.EmpLogService;
import com.hhh.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    private static final String DEFAULT_PASSWORD = "123456";

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;

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
    @Transactional(rollbackFor = Exception.class)
    public void add(Emp emp) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        emp.setCreateTime(now);
        emp.setUpdateTime(now);
        if (emp.getPassword() == null || emp.getPassword().isBlank()) {
            emp.setPassword(DEFAULT_PASSWORD);
        }
        empMapper.insert(emp);
        
        List<EmpExpr> exprList = !CollectionUtils.isEmpty(emp.getExprList()) ? emp.getExprList() : emp.getExprs();
        if (!CollectionUtils.isEmpty(exprList)) {
            for (EmpExpr expr : exprList) {
                expr.setEmpId(emp.getId());
            }
            empExprMapper.insertBatch(exprList);
        }
        
        // 记录日志
        EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工：" + emp);
        empLogService.insertLog(empLog);
    }

    @Override
    @Transactional
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
        
        // 只有当工作经历列表存在时才进行更新操作
        List<EmpExpr> exprList = !CollectionUtils.isEmpty(emp.getExprList()) ? emp.getExprList() : emp.getExprs();
        if (emp.getExprList() != null || emp.getExprs() != null) {
            empExprMapper.deleteByEmpId(emp.getId());
            if (!CollectionUtils.isEmpty(exprList)) {
                for (EmpExpr expr : exprList) {
                    expr.setEmpId(emp.getId());
                }
                empExprMapper.insertBatch(exprList);
            }
        }
    }


    @Override
    @Transactional
    public void delete(Integer id) {
        empExprMapper.deleteByEmpId(id);
        empMapper.deleteById(id);
    }
}
