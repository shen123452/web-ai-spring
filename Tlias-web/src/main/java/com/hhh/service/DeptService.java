package com.hhh.service;

import com.hhh.pojo.Dept;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DeptService {
    /**
     * 查询所有部门
     * @return 部门列表
     */
    @Select(" select * from dept order by update_time desc;")
    // 查询所有部门,返回部门列表
    List<Dept> list();
}

