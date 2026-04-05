package com.hhh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationLogVO {
    private String operateEmpName;
    private LocalDateTime operateTime;
    private String className;
    private String methodName;
    private Long costTime;
    private String methodParams;
    private String returnValue;
}
