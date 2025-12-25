package com.hhh.service;

import com.hhh.pojo.Dept;
import java.util.List;

public interface DeptService {
    /**
     * 查询所有部门
     * @return 部门列表
     */
    // 查询所有部门,返回部门列表
    List<Dept> list();

    // 根据id删除部门
    void delete(Integer id);

    // 新增部门
    void add(Dept dept);

    // 更新部门
    void update(Dept dept);
}

