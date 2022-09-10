package com.jianjian.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResultMsgEnum {
    SUCCESS(0, "成功"),
    FAIL(-1, "失败"),
    TEST_ERROR(400, "发生错误啦!");
    private int code;
    private String message;
}