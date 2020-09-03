package com.exception.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lryepoch
 * @date 2020/5/27 15:45
 * @description 返回值的最外层数据结构
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult implements Serializable {

    private static final long serialVersionUID = -2884790828192232980L;

    /**状态*/
    private boolean status;

    /**状态码*/
    private Integer code;

    /**状态信息*/
    private String msg;

    /**数据*/
    private Object data;

    //是否登录
//    private boolean isLogin=true;

    public static CommonResult success(Object data){
        return CommonResult.builder().status(true).code(ResultEnum.SUCCESS.getCode()).msg(ResultEnum.SUCCESS.getMsg()).data(data).build();
    }

    public static CommonResult success(){
        return success(null);
    }

    public static CommonResult fail(Integer code, String msg){
        return CommonResult.builder().status(false).code(code).msg(msg).build();
    }



}
