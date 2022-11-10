package com.jia.home.utils;

import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;

public class JWException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String msg;
    private Integer code = 500;


    public JWException(String msg){
        super(msg);
        this.msg = msg;
    }

    public JWException(String msg,Throwable throwable){
        super(msg,throwable);
        this.msg = msg;
    }

    public JWException(String msg,Integer code){
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public JWException(String msg,Integer code,Throwable throwable){
        super(msg,throwable);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
