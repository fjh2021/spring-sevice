package com.fjh.common.config;

import com.fjh.common.MyException;
import com.fjh.common.ResponseResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/3/30 15:33
 */
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult handleArgumentNotValid(MethodArgumentNotValidException e) {
        ObjectError error = e.getBindingResult().getAllErrors().get(0);
        String msg = "";
        if (error != null) {
            msg = error.getDefaultMessage();
        }
        return ResponseResult.success(msg);
    }

    @ExceptionHandler(MyException.class)
    public ResponseResult handleMyException(MyException e) {
        return new ResponseResult(e.getCode(), e.getMsg());
    }
}
