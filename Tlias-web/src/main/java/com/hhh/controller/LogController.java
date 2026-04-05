package com.hhh.controller;

import com.hhh.pojo.OperationLogVO;
import com.hhh.pojo.PageResult;
import com.hhh.pojo.Result;
import com.hhh.service.EmpLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class LogController {
    private final EmpLogService empLogService;

    @GetMapping("/page")
    public Result<PageResult<OperationLogVO>> page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        log.info("Page query operation logs: page={}, pageSize={}", page, pageSize);
        return Result.success(empLogService.page(page, pageSize));
    }
}
