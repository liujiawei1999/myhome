package com.jia.home.utils;

public class RespBean {
    private Integer status;
    private String msg;
    private Object object;


    public static RespBean ok(){
        return new RespBean(200,"success",null);
    }

    public static RespBean ok(Object obj){
        return new RespBean(200,"success",obj);
    }

    public static RespBean error(String msg){
        return new RespBean(500,msg,null);
    }

    public static RespBean error(Integer status , String msg){
        return new RespBean(status,msg,null);
    }
    public static RespBean error(String msg,Object obj){
        return new RespBean(500,msg,obj);
    }

    public RespBean() {
    }

    public RespBean(Integer status, String msg, Object object) {
        this.status = status;
        this.msg = msg;
        this.object = object;
    }

    public Integer getStatus() {
        return status;
    }

    public RespBean setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RespBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public RespBean setObject(Object object) {
        this.object = object;
        return this;
    }
}
