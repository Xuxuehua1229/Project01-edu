package com.guli.edu.handler;

import com.guli.common.constants.R;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    //对所有的异常进行相同的处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("出现了异常！");
    }

    //对特定的异常进行处理ArithmeticException
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("0不能为除数，出现了异常");
    }

    //自定义异常
    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R error(EduException e){
        e.printStackTrace();
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
