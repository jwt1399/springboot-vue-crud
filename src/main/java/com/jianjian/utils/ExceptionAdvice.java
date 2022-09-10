package com.jianjian.utils;

import com.jianjian.utils.Result;
import com.jianjian.utils.ResultMsgEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.message.AuthException;

/**
 * @author：简简
 * @createTime：[2022/9/9 22:00]
 **/
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public Result Execption(Exception e) {
        log.error("未知异常！", e);
        e.printStackTrace();
        return Result.fail(ResultMsgEnum.TEST_ERROR);
    }
}