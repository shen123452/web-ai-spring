package com.hhh.maper;

import com.hhh.pojo.Emp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

@Mapper
public interface EmpMapper {
    @Select("select count(*) from emp")
    Long count();

    @Select("select id, username, password, name, gender, phone, job, salary, image, entry_date, dept_id, " +
            "create_time, update_time from emp order by id limit #{offset}, #{pageSize}")
    List<Emp> list(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, password, name, gender, phone, job, salary, image, entry_date, dept_id, " +
            "create_time, update_time) values(#{username}, #{password}, #{name}, #{gender}, #{phone}, #{job}, " +
            "#{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    int insert(Emp emp);

    @Update("update emp set username=#{username}, password=#{password}, name=#{name}, gender=#{gender}, " +
            "phone=#{phone}, job=#{job}, salary=#{salary}, image=#{image}, entry_date=#{entryDate}, " +
            "dept_id=#{deptId}, update_time=#{updateTime} where id=#{id}")
    int update(Emp emp);

    @Delete("delete from emp where id=#{id}")
    int deleteById(Integer id);
}
