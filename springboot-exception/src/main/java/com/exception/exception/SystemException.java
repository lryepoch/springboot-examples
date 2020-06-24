package com.exception.exception;

import com.exception.config.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lryepoch
 * @date 2020/6/23 13:38
 * @description TODO 自定义异常
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class SystemException extends RuntimeException {

    /**错误码*/
    private Integer code;

    /**错误信息*/
    private String msg;

}
