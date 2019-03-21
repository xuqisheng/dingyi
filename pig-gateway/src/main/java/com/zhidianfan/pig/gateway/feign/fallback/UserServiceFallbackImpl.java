package com.zhidianfan.pig.gateway.feign.fallback;

import com.zhidianfan.pig.gateway.component.dto.TipCommon;
import com.zhidianfan.pig.gateway.feign.UserService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @author lengleng
 * @date 2017/10/31
 * why add @Service when i up version ?
 * https://github.com/spring-cloud/spring-cloud-netflix/issues/762
 */
@Slf4j
@Service
public class UserServiceFallbackImpl implements FallbackFactory<UserService> {


    @Override
    public UserService create(Throwable throwable) {
        log.info(throwable.getMessage());
        return new UserService() {
            @Override
            public TipCommon checkAuth(String username, String password, String clientType) {
                log.error("调用 UserService.checkAuth 异常：{}，{},{}", username, password, clientType);
                return new TipCommon(500, throwable.getMessage());
            }

            @Override
            public TipCommon checkAuthV2(String username, String clientType) {
                log.error("调用 UserService.checkAuthV2 异常：{}，{},{}", username, clientType);
                return new TipCommon(500, throwable.getMessage());
            }

            @Override
            public TipCommon checkUsernameUrl(String username, String url) {
                log.error("调用 UserService.checkUsernameUrl 异常：{}，{}", username, url);
                return new TipCommon(500, throwable.getMessage());
            }
        };
    }
}
