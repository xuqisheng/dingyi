
package com.zhidianfan.pig.log.feign;

import com.zhidianfan.pig.common.constant.Tip;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author lengleng
 * @date 2017/10/31
 */
@FeignClient(name = "pig-yd-user", fallbackFactory = UserServiceFallbackImpl.class)
public interface UserService {
    /**
     * 检查用户名、密码、客户端类型正确性
     *
     * @param username
     * @param password
     * @param clientType
     * @return
     */
    @PostMapping("/user/check")
    Tip checkAuth(@RequestParam(name = "username") String username
            , @RequestParam(name = "password") String password
            , @RequestParam(name = "clientType") String clientType);

}
