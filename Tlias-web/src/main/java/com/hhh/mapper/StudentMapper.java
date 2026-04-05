package com.hhh.mapper;

import com.hhh.pojo.Student;
import com.hhh.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<Student> list(StudentQueryParam studentQueryParam);

    @Select("""
            select s.id, s.name, s.no, s.gender, s.phone, s.id_card, s.is_college, s.address, s.degree,
                   s.graduation_date, s.clazz_id, s.violation_count, s.violation_score,
                   s.create_time, s.update_time, c.name as clazz_name
            from student s
            left join clazz c on s.clazz_id = c.id
            where s.id = #{id}
            """)
    Student getById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("""
            insert into student(name, no, gender, phone, id_card, is_college, address, degree, graduation_date,
                                clazz_id, violation_count, violation_score, create_time, update_time)
            values(#{name}, #{no}, #{gender}, #{phone}, #{idCard}, #{isCollege}, #{address}, #{degree},
                   #{graduationDate}, #{clazzId}, #{violationCount}, #{violationScore}, #{createTime}, #{updateTime})
            """)
    void insert(Student student);

    @Update("""
            update student
            set name = #{name},
                no = #{no},
                gender = #{gender},
                phone = #{phone},
                id_card = #{idCard},
                is_college = #{isCollege},
                address = #{address},
                degree = #{degree},
                graduation_date = #{graduationDate},
                clazz_id = #{clazzId},
                update_time = #{updateTime}
            where id = #{id}
            """)
    void update(Student student);

    @Delete("delete from student where id = #{id}")
    void deleteById(Integer id);

    @Update("""
            update student
            set violation_count = ifnull(violation_count, 0) + 1,
                violation_score = ifnull(violation_score, 0) + #{score},
                update_time = now()
            where id = #{id}
            """)
    void addViolation(@Param("id") Integer id, @Param("score") Integer score);
}
