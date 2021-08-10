package com.jack.rpcuserservice.applicationInit;

import com.jack.rpcuserservice.annotation.JackReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

@Component
public class ReferenceInvokeProxy implements BeanPostProcessor {

    @Autowired
    RemoteInvocationHandler invocationHandler;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        //获取所有字段
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(JackReference.class)) {
                field.setAccessible(true);
                Object proxy = Proxy.newProxyInstance(field.getType().getClassLoader(), new Class<?>[]{field.getType()}, invocationHandler);
                try {
                    //设置为代理对象
                    field.set(bean, proxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

}