package com.authority.common.exception;

import com.authority.common.domain.IErrorCode;

/**
 * @Author lryepoch
 * @CreateDate 2020/12/27 0:07
 * @Description 断言处理类，用于抛出各种API异常
 */
public class Asserts {
    public static void fail(String message){
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode){
        throw new ApiException(errorCode);
    }
}
