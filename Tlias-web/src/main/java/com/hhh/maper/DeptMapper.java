package com.hhh.maper;

import com.hhh.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper {
    /**
     * 查询所有部门
     * @return 部门列表
     */
    @Select("select id, name from dept")
    List<Dept> list();
}
