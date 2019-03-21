package com.zhidianfan.pig.gateway.ratelimit;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.DefaultRateLimitKeyGenerator;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义限流策略
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/8
 * @Modified By:
 */
@Component
public class UserRateLimitKeyGenerator extends DefaultRateLimitKeyGenerator {


    private Logger log = LoggerFactory.getLogger(getClass());

    public UserRateLimitKeyGenerator(RateLimitProperties properties) {
        super(properties);
    }

    @Override
    public String key(HttpServletRequest request, Route route, RateLimitProperties.Policy policy) {
        String url = request.getRequestURI();
        log.info("对 {} 进行限流", url);
        String ramiteKey = super.key(request, route, policy);
        log.info("ramiteKey:{}", ramiteKey);
        return ramiteKey;


    }
}
