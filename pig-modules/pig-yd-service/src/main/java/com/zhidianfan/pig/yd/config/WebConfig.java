package com.zhidianfan.pig.yd.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * web端配置信息
 *
 * @author danda
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * 配置自己的国际化语言解析器
     *
     * @return
     */
    @Bean
    public LocaleResolver localeResolver(RedisTemplate redisTemplate) {
        return new MyLocaleResolver(redisTemplate);
    }

    @Bean
    public FilterRegistrationBean setFilter(){
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new VersionFilter());
        filterBean.setName("VersionFilter");
        filterBean.addUrlPatterns("/*");
        return filterBean;
    }

    /**
     * 配置自己的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //super.addInterceptors(registry);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
