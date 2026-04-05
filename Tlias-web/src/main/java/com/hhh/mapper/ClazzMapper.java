package com.hhh.mapper;

import com.hhh.pojo.Clazz;
import com.hhh.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ClazzMapper {
    List<Clazz> list(ClazzQueryParam clazzQueryParam);

    @Select("""
            select c.id, c.name, c.room, c.begin_date, c.end_date, c.master_id, c.subject,
                   c.create_time, c.update_time, e.name as master_name,
                   case
                       when curdate() < c.begin_date then '未开班'
                       when curdate() > c.end_date then '已结课'
                       else '进行中'
                   end as status
            from clazz c
            left join emp e on c.master_id = e.id
            order by c.update_time desc
            """)
    List<Clazz> listAll();

    @Select("""
            select c.id, c.name, c.room, c.begin_date, c.end_date, c.master_id, c.subject,
                   c.create_time, c.update_time, e.name as master_name,
                   case
                       when curdate() < c.begin_date then '未开班'
                       when curdate() > c.end_date then '已结课'
                       else '进行中'
                   end as status
            from clazz c
            left join emp e on c.master_id = e.id
            where c.id = #{id}
            """)
    Clazz getById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("""
            insert into clazz(name, room, begin_date, end_date, master_id, subject, create_time, update_time)
            values(#{name}, #{room}, #{beginDate}, #{endDate}, #{masterId}, #{subject}, #{createTime}, #{updateTime})
            """)
    void insert(Clazz clazz);

    @Update("""
            update clazz
            set name = #{name},
                room = #{room},
                begin_date = #{beginDate},
                end_date = #{endDate},
                master_id = #{masterId},
                subject = #{subject},
                update_time = #{updateTime}
            where id = #{id}
            """)
    void update(Clazz clazz);

    @Delete("delete from clazz where id = #{id}")
    void deleteById(Integer id);

    @Select("select count(*) from student where clazz_id = #{clazzId}")
    Integer countStudentByClazzId(Integer clazzId);
}
