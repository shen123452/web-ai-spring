package com.hhh.controller;

import com.hhh.pojo.JobOption;
import com.hhh.pojo.Result;
import com.hhh.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/empJobData")
    public Result<JobOption> getEmpJobData() {
        log.info("Query employee job report");
        return Result.success(reportService.getEmpJobData());
    }

    @GetMapping("/empGenderData")
    public Result<List<Map<String, Object>>> getEmpGenderData() {
        log.info("Query employee gender report");
        return Result.success(reportService.getEmpGenderData());
    }

    @GetMapping("/studentCountData")
    public Result<Map<String, Object>> getStudentCountData() {
        log.info("Query student count report");
        return Result.success(reportService.getStudentCountData());
    }

    @GetMapping("/studentDegreeData")
    public Result<List<Map<String, Object>>> getStudentDegreeData() {
        log.info("Query student degree report");
        return Result.success(reportService.getStudentDegreeData());
    }
}
