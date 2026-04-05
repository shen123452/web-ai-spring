package com.hhh.service;

import com.hhh.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    JobOption getEmpJobData();

    List<Map<String, Object>> getEmpGenderData();

    Map<String, Object> getStudentCountData();

    List<Map<String, Object>> getStudentDegreeData();
}
