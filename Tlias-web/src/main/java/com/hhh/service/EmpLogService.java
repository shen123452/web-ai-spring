package com.hhh.service;

import com.hhh.pojo.EmpLog;
import com.hhh.pojo.OperationLogVO;
import com.hhh.pojo.PageResult;

public interface EmpLogService {
    void insertLog(EmpLog empLog);

    PageResult<OperationLogVO> page(Integer page, Integer pageSize);
}
