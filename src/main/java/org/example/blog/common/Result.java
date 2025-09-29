package org.example.blog.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    // ===== 静态工具 =====
    public static Result<?> ok() {
        return new Result<>(200, "success", null);
    }
    public static <T> Result<T> ok(T data) {
        return new Result<>(200, "success", data);
    }
    public static Result<?> error(String msg) {
        return new Result<>(400, msg, null);
    }
}