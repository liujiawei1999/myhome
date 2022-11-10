package com.jia.home.exception;


import com.jia.home.utils.JWException;
import com.jia.home.utils.RespBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class JWExceptionHandler {

    @ExceptionHandler(JWException.class)
    public RespBean handleJWException(JWException e){
        RespBean respBean = new RespBean();
        respBean.setStatus(e.getCode());
        respBean.setMsg(e.getMsg());
        return respBean;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public RespBean handlerNoFoundException(Exception e){
        return RespBean.error(404,"路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public RespBean handlerDuplicateKeyException(DuplicateKeyException e){
        return RespBean.error("数据库中已存在该记录");
    }

    @ExceptionHandler(Exception.class)
    public RespBean handlerException(Exception e){
        return RespBean.error("系统未知异常");
    }
}
