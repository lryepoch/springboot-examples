package com.exception.util;

import com.exception.config.ResultEnum;
import com.exception.exception.SystemException;
import org.springframework.validation.BindingResult;

/**
 * @author lryepoch
 * @date 2020/6/24 16:21
 * @description TODO 判断参数是否出错工具
 */
public class ParamValidateUtil {
    public static void checkParams(BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new SystemException(ResultEnum.ERR.getCode(),ResultEnum.ERR.getMsg());
        }
    }
}
