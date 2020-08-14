package com.jobeth.blog.advice;

import com.jobeth.blog.common.enums.ResultEnum;
import com.jobeth.blog.common.exception.ServerException;
import com.jobeth.blog.vo.JsonResultVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Desc
 *
 * @author Jobeth
 * @since 2020/4/30 16:12
 */
@RestControllerAdvice
public class GlobalExceptionControllerAdvice {

    /***
     * 自定义异常
     * @return ResultVO
     */
    @ExceptionHandler(Exception.class)
    public JsonResultVO<Object> exception(Exception e) {
        //自定义异常
        if (e instanceof ServerException) {
            ServerException serverException = (ServerException) e;
            ResultEnum resultEnum = serverException.getResultEnum();
            return new JsonResultVO<>(resultEnum);
        }
        //访问方式不匹配
        else if (e instanceof HttpRequestMethodNotSupportedException) {
            return new JsonResultVO<>(ResultEnum.CHECK_REQUEST_METHOD);
        }
        //访问地址匹配不上
        else if (e instanceof NoHandlerFoundException) {
            return new JsonResultVO<>(ResultEnum.CHECK_REQUEST_URL);
        }
        //访问参数有误（为空或者类型不匹配）
        else if (e instanceof MethodArgumentTypeMismatchException) {
            return new JsonResultVO<>(ResultEnum.REQUEST_PARAMETER_ERROR);
        }
        //未知异常
        else {
            return new JsonResultVO<>(ResultEnum.INTERNAL_SERVER_ERROR);
        }
    }
}