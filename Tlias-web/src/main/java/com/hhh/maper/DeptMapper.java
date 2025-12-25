package com.hhh.maper;

import com.hhh.pojo.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeptMapper {
    /**
     * 查询所有部门
     * @return 部门列表
     */

    @Select("select id, name, create_time, update_time from dept")
    List<Dept> list();

    @Delete("delete from dept where id = #{id}")
    int deleteById(Integer id);

    @Insert("insert into dept(name, create_time, update_time) values(#{name}, #{createTime}, #{updateTime})")
    int insert(Dept dept);

    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    int update(Dept dept);
}
