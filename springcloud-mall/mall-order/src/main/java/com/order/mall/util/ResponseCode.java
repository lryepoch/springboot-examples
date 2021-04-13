package com.order.mall.util;

/**
 * @author lryepoch
 * @date 2021/4/13 11:49
 * @description TODO
 */
public enum ResponseCode {
    success(200, "请求成功"),
    fail(400, "请求失败"),
    error(500, "服务端错误"),
    serviceFuse(700, "请求熔断,稍后重试"),
    serviceFlow(701, "请求限流,稍后重试"),
    serviceHotspot(702, "请求热点参数限流,稍后重试"),
    serviceSystem(703, "请求触发系统保护规则,稍后重试"),
    serviceRules(704, "请求Sentinel授权规则不通过,稍后重试"),
    unkown(999, "未知类型"),
    ;

    private int code;
    private String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ResponseCode getByCode(int code) {
        for (ResponseCode responseCode : ResponseCode.values()) {
            if (responseCode.code() == (code)) {
                return responseCode;
            }
        }
        return unkown;
    }

    public int code() {
        return this.code;
    }

    public String desc() {
        return this.desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
