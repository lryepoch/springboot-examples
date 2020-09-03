package com.exception.config;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lryepoch
 * @date 2020/5/27 15:57
 * @description 返回值类型
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    //请求成功
    SUCCESS(200,"操作成功"),

    //业务错误
    ERR(400,"操作失败"),

    UNAUTHORIZED(401,"未认证"),

    UNCONNECT(402,"未连接"),

    NO_DATA(403,"数据为空"),

    NOT_FOUND(404,"接口不存在"),

    RUN_SQL_ERROR(1000,"执行SQL异常"),

    //服务器内部错误
    INTERNAL_SERVER_ERROR(500,"稍等片刻，管理员正在努力恢复中");

    private Integer code;

    private String msg;


}
