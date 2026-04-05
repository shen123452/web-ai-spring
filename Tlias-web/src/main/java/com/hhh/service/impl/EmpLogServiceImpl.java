package com.hhh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hhh.mapper.EmpLogMapper;
import com.hhh.pojo.EmpLog;
import com.hhh.pojo.OperationLogVO;
import com.hhh.pojo.PageResult;
import com.hhh.service.EmpLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmpLogServiceImpl implements EmpLogService {
    private final EmpLogMapper empLogMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void insertLog(EmpLog empLog) {
        empLogMapper.insert(empLog);
    }

    @Override
    public PageResult<OperationLogVO> page(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        Page<EmpLog> logPage = (Page<EmpLog>) empLogMapper.list();
        List<OperationLogVO> rows = logPage.getResult().stream().map(this::toVO).toList();
        return new PageResult<>(logPage.getTotal(), rows);
    }

    private OperationLogVO toVO(EmpLog log) {
        Map<String, String> parsed = parseInfo(log.getInfo());
        String action = parsed.getOrDefault("action", "");
        String className = switch (action) {
            case "ADD_EMP", "UPDATE_EMP", "DELETE_EMP" -> "EmpService";
            case "ADD_CLAZZ", "UPDATE_CLAZZ", "DELETE_CLAZZ" -> "ClazzService";
            case "ADD_STUDENT", "UPDATE_STUDENT", "DELETE_STUDENT", "VIOLATION_STUDENT" -> "StudentService";
            default -> "-";
        };
        String methodName = switch (action) {
            case "ADD_EMP", "ADD_CLAZZ", "ADD_STUDENT" -> "add";
            case "UPDATE_EMP", "UPDATE_CLAZZ", "UPDATE_STUDENT" -> "update";
            case "DELETE_EMP", "DELETE_CLAZZ", "DELETE_STUDENT" -> "delete";
            case "VIOLATION_STUDENT" -> "violation";
            default -> "-";
        };

        String rawInfo = log.getInfo() == null ? "" : log.getInfo();
        String detail = parsed.getOrDefault("detail", rawInfo);

        return new OperationLogVO(
                parsed.getOrDefault("operator", "未知"),
                log.getOperateTime(),
                className,
                methodName,
                0L,
                detail,
                "success"
        );
    }

    private Map<String, String> parseInfo(String info) {
        Map<String, String> result = new HashMap<>();
        if (info == null || info.isBlank()) {
            return result;
        }
        String[] parts = info.split(";");
        for (String part : parts) {
            int idx = part.indexOf('=');
            if (idx <= 0 || idx == part.length() - 1) {
                continue;
            }
            result.put(part.substring(0, idx).trim(), part.substring(idx + 1).trim());
        }
        return result;
    }
}
