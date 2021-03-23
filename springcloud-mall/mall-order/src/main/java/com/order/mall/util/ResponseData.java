package com.order.mall.util;


/**
 * 定义下返回类，如果项目有自己的返回类，那么就用自己项目定义的返回类统一处理，这里是为了方便测试
 */
class ResponseData {
    private int code;
    private String message;

    public ResponseData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
