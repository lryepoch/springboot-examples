package com.authority.common.api;

/**
 * @author lryepoch
 * @date 2020/12/25 18:45
 * @description TODO 封装API的错误码
 */
public interface IErrorCode {
    long getCode();
    String getMessage();
}
