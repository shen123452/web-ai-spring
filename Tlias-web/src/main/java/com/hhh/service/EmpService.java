package com.hhh.service;

import com.hhh.pojo.Emp;
import com.hhh.pojo.EmpQueryParam;
import com.hhh.pojo.LoginRequest;
import com.hhh.pojo.PageResult;

import java.util.List;

public interface EmpService {
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    List<Emp> list();

    void add(Emp emp);

    Emp getById(Integer id);

    void update(Emp emp);

    void delete(Integer id);

    void delete(List<Integer> ids);

    Emp login(LoginRequest loginRequest);
}
