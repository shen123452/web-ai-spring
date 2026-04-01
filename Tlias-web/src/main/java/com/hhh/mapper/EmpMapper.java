package com.hhh.mapper;

import com.hhh.pojo.Emp;
import com.hhh.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    //@Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id=d.id order by e.update_time desc")
//    List<Emp> list(@Param("name") String name,
//                   @Param("gender") Integer gender,
//                   @Param("begin") LocalDate begin,
//                   @Param("end") LocalDate end);
    List<Emp> list(EmpQueryParam empQueryParam);

    @Options(useGeneratedKeys = true, keyProperty = "id")//主键传递
    @Insert("insert into emp(username, password, name, gender, phone, job, salary, image, entry_date, dept_id, " +
            "create_time, update_time) values(#{username}, #{password}, #{name}, #{gender}, #{phone}, #{job}, " +
            "#{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    void insert(Emp emp);

    @Update("update emp set username=#{username}, password=#{password}, name=#{name}, gender=#{gender}, " +
            "phone=#{phone}, job=#{job}, salary=#{salary}, image=#{image}, entry_date=#{entryDate}, " +
            "dept_id=#{deptId}, update_time=#{updateTime} where id=#{id}")
   void update(Emp emp);

    @Delete("delete from emp where id=#{id}")
   void deleteById(Integer id);

    List<Map<String, Object>> countEmpJobData();
}
