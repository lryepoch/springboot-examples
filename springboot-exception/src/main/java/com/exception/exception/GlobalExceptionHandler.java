package com.exception.exception;

import com.exception.config.ResultEnum;
import com.exception.config.CommonResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @author lryepoch
 * @dae 2020/6/23 16:29
 * @description TODO 全局异常捕获类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        System.out.println("请求有参数才进来");
    }


    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     *
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "嘟嘟MD");
    }


    /**
     * 自定义业务异常
     */
    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public CommonResult systemExceptionHandler(HttpServletRequest request, CustomException e) {
        CommonResult commonResult = CommonResult.fail(e.getCode(), e.getMsg());

        StringBuilder data = new StringBuilder();
        data.append("方法1：")
                .append(request.getRequestURI())
                .append("，")
                .append("错误原因：")
                .append(e.getMsg());
        commonResult.setData(data.toString());

        return commonResult;
    }


    /**
     * 不可查异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResult exceptionHandler(HttpServletRequest request, Exception e) {
        CommonResult commonResult;

        //sql异常
        if (e instanceof SQLException) {
            commonResult = CommonResult.fail(ResultEnum.RUN_SQL_ERROR.getCode(), ResultEnum.RUN_SQL_ERROR.getMsg());
            //NPE异常
        } else if (e instanceof NullPointerException) {
            commonResult = CommonResult.fail(ResultEnum.NO_DATA.getCode(), ResultEnum.NO_DATA.getMsg());
        } else {
            //其他异常
            commonResult = CommonResult.fail(ResultEnum.ERR.getCode(), ResultEnum.ERR.getMsg());
        }

        StringBuilder data = new StringBuilder();
        data.append("方法2：")
                .append(request.getRequestURI())
                .append("，")
                .append("错误原因：")
                .append(e.getMessage());
        commonResult.setData(data.toString());

        return commonResult;
    }
}
