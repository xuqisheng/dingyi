package com.zhidianfan.pig.yd.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.websocket.server.ServerEndpointConfig;

/**
 * @author sjl
 * 2019-02-28 15:36
 */
public class SpringApplicationContext extends ServerEndpointConfig.Configurator implements ApplicationContextAware {
    private static volatile ApplicationContext applicationContext;

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationContext.applicationContext = applicationContext;
    }
}
