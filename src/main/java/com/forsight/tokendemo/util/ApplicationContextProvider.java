package com.forsight.tokendemo.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component("applicationContext")
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {

        return applicationContext;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext context) throws BeansException {

        applicationContext = context;
    }

    public static <T> T getBean(Class<T> t) {

        return applicationContext.getBean(t);
    }

}
