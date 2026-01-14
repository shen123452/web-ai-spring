package com.hhh.maper;

import com.hhh.pojo.EmpExpr;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    @Delete("delete from emp_expr where emp_id = #{empId}")
    int deleteByEmpId(Integer empId);

    @Insert("<script>" +
            "insert into emp_expr(emp_id, begin, end, company, job) values " +
            "<foreach collection='exprs' item='expr' separator=','>" +
            "(#{expr.empId}, #{expr.begin}, #{expr.end}, #{expr.company}, #{expr.job})" +
            "</foreach>" +
            "</script>")
    int insertBatch(@Param("exprs") List<EmpExpr> exprs);
}
