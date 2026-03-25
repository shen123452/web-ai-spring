package com.hhh.mapper;

import com.hhh.pojo.EmpExpr;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface  EmpExprMapper {
    @Delete("delete from emp_expr where emp_id = #{empId}")
    int deleteByEmpId(Integer empId);

    void insertBatch(@Param("exprs") List<EmpExpr> exprs);
}
