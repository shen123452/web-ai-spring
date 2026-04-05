package com.hhh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hhh.mapper.EmpExprMapper;
import com.hhh.mapper.EmpMapper;
import com.hhh.pojo.Emp;
import com.hhh.pojo.EmpExpr;
import com.hhh.pojo.EmpLog;
import com.hhh.pojo.EmpQueryParam;
import com.hhh.pojo.LoginRequest;
import com.hhh.pojo.PageResult;
import com.hhh.service.EmpLogService;
import com.hhh.service.EmpService;
import com.hhh.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {
    private static final String DEFAULT_PASSWORD = "123456";

    private final EmpMapper empMapper;
    private final EmpExprMapper empExprMapper;
    private final EmpLogService empLogService;

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
        Page<Emp> empPage = (Page<Emp>) empMapper.list(empQueryParam);
        return new PageResult<>(empPage.getTotal(), empPage.getResult());
    }

    @Override
    public List<Emp> list() {
        return empMapper.listAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Emp emp) {
        List<EmpExpr> exprList = resolveExprList(emp);
        validateExprList(exprList);
        LocalDateTime now = LocalDateTime.now();
        emp.setCreateTime(now);
        emp.setUpdateTime(now);
        if (!StringUtils.hasText(emp.getPassword())) {
            emp.setPassword(DEFAULT_PASSWORD);
        }
        empMapper.insert(emp);
        saveExprList(emp.getId(), exprList);
        writeLog(now, "ADD_EMP", emp.getUsername());
    }

    @Override
    public Emp getById(Integer id) {
        Emp emp = empMapper.getById(id);
        if (emp == null) {
            return null;
        }
        List<EmpExpr> exprList = empExprMapper.listByEmpId(id);
        emp.setExprList(exprList);
        emp.setExprs(exprList);
        return emp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Emp emp) {
        Emp dbEmp = empMapper.getById(emp.getId());
        if (dbEmp == null) {
            throw new IllegalArgumentException("员工不存在");
        }
        List<EmpExpr> exprList = resolveExprList(emp);
        validateExprList(exprList);
        emp.setUpdateTime(LocalDateTime.now());
        if (!StringUtils.hasText(emp.getPassword())) {
            emp.setPassword(null);
        }
        empMapper.update(emp);

        if (emp.getExprList() != null || emp.getExprs() != null) {
            empExprMapper.deleteByEmpId(emp.getId());
            saveExprList(emp.getId(), exprList);
        }
        writeLog(LocalDateTime.now(), "UPDATE_EMP", String.valueOf(emp.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        delete(List.of(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (Integer id : ids) {
            Emp emp = empMapper.getById(id);
            if (emp == null) {
                throw new IllegalArgumentException("员工不存在");
            }
            empExprMapper.deleteByEmpId(id);
            empMapper.deleteById(id);
        }
        writeLog(LocalDateTime.now(), "DELETE_EMP", ids.toString());
    }

    @Override
    public Emp login(LoginRequest loginRequest) {
        if (loginRequest == null
                || !StringUtils.hasText(loginRequest.getUsername())
                || !StringUtils.hasText(loginRequest.getPassword())) {
            throw new IllegalArgumentException("用户名和密码不能为空");
        }

        Emp emp = empMapper.getByUsername(loginRequest.getUsername().trim());
        if (emp == null) {
            return null;
        }
        return Objects.equals(emp.getPassword(), loginRequest.getPassword()) ? emp : null;
    }

    private List<EmpExpr> resolveExprList(Emp emp) {
        if (!CollectionUtils.isEmpty(emp.getExprList())) {
            return emp.getExprList();
        }
        if (!CollectionUtils.isEmpty(emp.getExprs())) {
            return emp.getExprs();
        }
        return List.of();
    }

    private void saveExprList(Integer empId, List<EmpExpr> exprList) {
        if (CollectionUtils.isEmpty(exprList)) {
            return;
        }
        for (EmpExpr expr : exprList) {
            expr.setEmpId(empId);
        }
        empExprMapper.insertBatch(exprList);
    }

    private void validateExprList(List<EmpExpr> exprList) {
        if (CollectionUtils.isEmpty(exprList)) {
            return;
        }
        for (EmpExpr expr : exprList) {
            if (expr.getBegin() != null && expr.getEnd() != null && expr.getEnd().isBefore(expr.getBegin())) {
                throw new IllegalArgumentException("工作经历结束时间不能早于开始时间");
            }
        }
    }

    private void writeLog(LocalDateTime time, String action, String detail) {
        String info = "operator=" + UserContext.getUsernameOrDefault("system")
                + ";action=" + action
                + ";detail=" + detail;
        empLogService.insertLog(new EmpLog(null, time, info));
    }
}
