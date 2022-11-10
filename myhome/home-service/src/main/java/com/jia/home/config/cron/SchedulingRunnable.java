package com.jia.home.config.cron;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 每一个定时任务都对应一个子线程
 */
public class SchedulingRunnable implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(SchedulingRunnable.class);


    private String beanName;
    private String methodName;

    private String params;

    private Object targetBean;
    private Method method;

    public SchedulingRunnable(String beanName, String methodName) {
       this(beanName,methodName,null);
    }

    public SchedulingRunnable(String beanName, String methodName, String params) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.params = params;
        init();
    }

    public void init(){
        try {
            targetBean =  SpringContextUtils.getBean(beanName);
            if (StringUtils.hasText(params)){
                 method = targetBean.getClass().getDeclaredMethod(methodName,String.class);
            }else{
                method = targetBean.getClass().getDeclaredMethod(methodName);
            }
            //使方法可访问
            ReflectionUtils.makeAccessible(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    /**
     * 定时任务 时间到了就执行
     */
    @Override
    public void run() {
        logger.info("定时任务开始执行-bean:{},方法:{},参数:{}",beanName,method,params);
        long startMillis = System.currentTimeMillis();
        try {
            if (StringUtils.hasText(params)){
                method.invoke(targetBean,params);
            }else {
                method.invoke(targetBean);
            }
        } catch (Exception e) {
            logger.error(String.format("定时任务执行错误-bean:%s,方法:%s,参数:%s",beanName,method,params),e);
        }
        long endMillis = System.currentTimeMillis();
        logger.info("定时任务结束执行-bean:{},方法:{},参数:{},耗时:{}毫秒",beanName,method,params,(endMillis-startMillis));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchedulingRunnable that = (SchedulingRunnable) o;
        return Objects.equals(beanName, that.beanName) &&
                Objects.equals(methodName, that.methodName) &&
                Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanName, methodName, params);
    }
}
