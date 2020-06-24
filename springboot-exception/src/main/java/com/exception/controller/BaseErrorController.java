package com.exception.controller;

import com.exception.config.ResultEnum;
import com.exception.exception.SystemException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author lryepoch
 * @date 2020/6/23 19:06
 * @description TODO
 */
@RestController
public class BaseErrorController {


    @RequestMapping("/json")
    public void json(ModelMap modelMap) throws Exception {
        System.out.println(modelMap.get("author"));
//        int i = 5 / 0;
//        int[] a = {1,2};
//        int b=a[2];
//        System.out.println(b);
        throw new SystemException(ResultEnum.UNAUTHORIZED.getCode(),ResultEnum.UNAUTHORIZED.getMsg());
//        throw new SQLSyntaxErrorException("hhahhaha");
    }
}