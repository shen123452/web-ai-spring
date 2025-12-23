package com.hhh.service.impl;

import com.hhh.maper.DeptMapper;
import com.hhh.pojo.Dept;
import com.hhh.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    // 查询所有部门,返回部门列表
    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }
}
