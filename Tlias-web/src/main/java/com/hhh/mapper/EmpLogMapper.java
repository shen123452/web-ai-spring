package com.hhh.mapper;

import com.hhh.pojo.EmpLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmpLogMapper {
    @Insert("insert into emp_log(operate_time, info) values(#{operateTime}, #{info})")
    void insert(EmpLog empLog);

    @Select("select id, operate_time, info from emp_log order by operate_time desc")
    List<EmpLog> list();
}
