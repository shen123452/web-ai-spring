package com.hhh.mapper;

import com.hhh.pojo.Emp;
import com.hhh.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    List<Emp> list(EmpQueryParam empQueryParam);

    @Select("""
            select id, username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time
            from emp
            order by update_time desc
            """)
    List<Emp> listAll();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("""
            insert into emp(username, password, name, gender, phone, job, salary, image, entry_date, dept_id,
                            create_time, update_time)
            values(#{username}, #{password}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image},
                   #{entryDate}, #{deptId}, #{createTime}, #{updateTime})
            """)
    void insert(Emp emp);

    void update(Emp emp);

    @Delete("delete from emp where id = #{id}")
    void deleteById(Integer id);

    @Select("""
            select e.id, e.username, e.name, e.gender, e.phone, e.job, e.salary, e.image,
                   e.entry_date, e.dept_id, e.create_time, e.update_time, d.name as dept_name
            from emp e
            left join dept d on e.dept_id = d.id
            where e.id = #{id}
            """)
    Emp getById(Integer id);

    @Select("""
            select id, username, password, name, gender, phone, job, salary, image,
                   entry_date, dept_id, create_time, update_time
            from emp
            where username = #{username}
            limit 1
            """)
    Emp getByUsername(String username);

    @Select("select count(*) from emp where dept_id = #{deptId}")
    Integer countByDeptId(Integer deptId);

    @Select("""
            select case job
                       when 1 then '班主任'
                       when 2 then '讲师'
                       when 3 then '学工主管'
                       when 4 then '教研主管'
                       when 5 then '咨询师'
                       else '其他'
                   end as pos,
                   count(*) as total
            from emp
            group by job
            order by job
            """)
    List<Map<String, Object>> countEmpJobData();
}
