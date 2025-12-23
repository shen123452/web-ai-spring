package com.hhh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private Object data;

    public static  Result success() {
        return new Result(200, "success", null);
    }

    public static Result success(Object data) {
        return new Result(200, "success", data);
    }

    public static Result error(String msg) {
        return new Result(500, msg, null);
    }
}
