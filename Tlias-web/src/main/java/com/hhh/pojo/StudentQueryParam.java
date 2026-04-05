package com.hhh.pojo;

import lombok.Data;

@Data
public class StudentQueryParam {
    private Integer page = 1;
    private Integer pageSize = 10;
    private Integer clazzId;
    private Integer degree;
    private String name;
}
