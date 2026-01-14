package com.hhh.service;

import com.hhh.pojo.Emp;
import com.hhh.pojo.PageResult;

public interface EmpService {
    PageResult<Emp> page(Integer page, Integer pageSize);

    void add(Emp emp);

    void update(Emp emp);

    void delete(Integer id);
}
