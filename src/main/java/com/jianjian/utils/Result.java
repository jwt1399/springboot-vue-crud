package com.jianjian.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author：简简
 * @createTime：[2022/9/9 20:13]
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    /**
     * 成功
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(ResultMsgEnum.SUCCESS.getCode());
        result.setMessage(ResultMsgEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 失败
     */
    public static <T> Result<T> fail(ResultMsgEnum resultMsgEnum) {
        Result<T> result = new Result<T>();
        result.setCode(resultMsgEnum.getCode());
        result.setMessage(resultMsgEnum.getMessage());
        return result;
    }
}