package com.authority.common.exception;

import com.authority.common.domain.IErrorCode;

/**
 * @author lryepoch
 * @date 2020/12/25 18:55
 * @description TODO 自定义API异常
 */
public class ApiException extends RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode(){
        return errorCode;
    }

}
