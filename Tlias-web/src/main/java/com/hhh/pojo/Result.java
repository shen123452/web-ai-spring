package com.hhh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private static final int SUCCESS_CODE = 1;
    private static final int ERROR_CODE = 0;

    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success() {
        return new Result<>(SUCCESS_CODE, "success", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, "success", data);
    }

    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<>(ERROR_CODE, msg, null);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(ERROR_CODE, msg, null);
    }

    public static <T> Result<T> unauthorized(String msg) {
        return new Result<>(ERROR_CODE, msg, null);
    }
}
