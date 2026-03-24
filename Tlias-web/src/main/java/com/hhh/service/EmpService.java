package com.hhh.service;

import com.hhh.pojo.Emp;
import com.hhh.pojo.EmpQueryParam;
import com.hhh.pojo.PageResult;
public interface EmpService {
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    void add(Emp emp);//新增员工

    void update(Emp emp);

    void delete(Integer id);
}
