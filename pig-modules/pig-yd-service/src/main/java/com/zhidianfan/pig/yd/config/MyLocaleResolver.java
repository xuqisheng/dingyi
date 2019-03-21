package com.zhidianfan.pig.yd.config;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhidianfan.pig.common.util.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.LocaleResolver;

/**
 * 自定义国际化语言解析器
 */
public class MyLocaleResolver implements LocaleResolver {

    private static final String I18N_LANGUAGE = "i18n_language";
    private Logger log = LoggerFactory.getLogger(getClass());

    private RedisTemplate redisTemplate;
    private String local = "zh_CN";//默认环境 en_US 英文环境

    public MyLocaleResolver(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public MyLocaleResolver(RedisTemplate redisTemplate, String local) {
        this.redisTemplate = redisTemplate;
        this.local = local;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest req) {
        String i18n_language = req.getParameter(I18N_LANGUAGE);
        Locale locale = null;
        String username = UserUtils.getUserName();//用户名为唯一键
        String k = "LANGUAGE:" + username;
        if (!StringUtils.isEmpty(i18n_language)) {//客户请求中设置了值，以设置的国际化信息为准
            String[] language = i18n_language.split("_");
            locale = new Locale(language[0], language[1]);
            redisTemplate.opsForValue().set(k, locale);
            log.info("设置当前用户的语言环境：{}", i18n_language);

        } else {//客户未在请求中设置国际化参数

            Locale v = (Locale) redisTemplate.opsForValue().get(k);
            if (v != null) {
                locale = v;
                log.info("从缓存中获取语言环境：{}", v.getLanguage());
            } else {
                //设置默认值
                String[] language = local.split("_");
                locale = new Locale(language[0], language[1]);
                redisTemplate.opsForValue().set(k, locale);
                log.info("设置当前用户的默认语言环境：{}", local);

            }
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest req, HttpServletResponse res, Locale locale) {

    }

}  