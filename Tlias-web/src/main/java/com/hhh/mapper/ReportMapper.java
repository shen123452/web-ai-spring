package com.hhh.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {
    @Select("""
            select case gender
                       when 1 then '男'
                       when 2 then '女'
                       else '未知'
                   end as name,
                   count(*) as value
            from emp
            group by gender
            order by gender
            """)
    List<Map<String, Object>> countEmpGenderData();

    @Select("""
            select c.name as clazzName, count(s.id) as total
            from clazz c
            left join student s on s.clazz_id = c.id
            group by c.id, c.name
            order by c.id
            """)
    List<Map<String, Object>> countStudentCountData();

    @Select("""
            select case degree
                       when 1 then '初中'
                       when 2 then '高中'
                       when 3 then '大专'
                       when 4 then '本科'
                       when 5 then '硕士'
                       when 6 then '博士'
                       else '其他'
                   end as name,
                   count(*) as value
            from student
            group by degree
            order by degree
            """)
    List<Map<String, Object>> countStudentDegreeData();
}
