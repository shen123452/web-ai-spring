package com.hhh.service.impl;

import com.hhh.mapper.EmpMapper;
import com.hhh.mapper.ReportMapper;
import com.hhh.pojo.JobOption;
import com.hhh.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final EmpMapper empMapper;
    private final ReportMapper reportMapper;

    @Override
    public JobOption getEmpJobData() {
        List<Map<String, Object>> list = empMapper.countEmpJobData();
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("total")).toList();
        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return reportMapper.countEmpGenderData();
    }

    @Override
    public Map<String, Object> getStudentCountData() {
        List<Map<String, Object>> list = reportMapper.countStudentCountData();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("clazzList", list.stream().map(item -> item.get("clazzName")).toList());
        result.put("dataList", list.stream().map(item -> item.get("total")).toList());
        return result;
    }

    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return reportMapper.countStudentDegreeData();
    }
}
