package com.zhidianfan.pig.yd.moduler.user;

import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.Tip;
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
            public Tip checkAuth(String username, String password, String clientType) {
                log.error("调用 UserService.checkAuth 异常：{}，{},{}", username, password, clientType);
                return new ErrorTip(500,throwable.getMessage());
            }
        };
    }
}
