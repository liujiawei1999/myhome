package com.jia.home.config.cron;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 根据用户传入的Bean名称去spring 容器中查找相应的Bean
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 根据名称获取bean
     * @param name
     * @return
     */
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    /**
     * 根据类型获取bean
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> requiredType){
        return applicationContext.getBean(requiredType);
    }

    /**
     * 根据名字和类型
     * @param name
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name,Class<T> requiredType){
        return applicationContext.getBean(name,requiredType);
    }

    /**
     * 判断容器中是否包含bean
     * @param name
     * @return
     */
    public static boolean containsBean(String name){
        return applicationContext.containsBean(name);
    }

    /**
     * 判断bean是否是单例
     * @param name
     * @return
     */
    public static boolean isSingleton(String name){
        return applicationContext.isSingleton(name);
    }

    /**
     * 根据名字获取类型
     * @param name
     * @return
     */
    public static Class<? extends Object> getType(String name){
        return applicationContext.getType(name);
    }


}
