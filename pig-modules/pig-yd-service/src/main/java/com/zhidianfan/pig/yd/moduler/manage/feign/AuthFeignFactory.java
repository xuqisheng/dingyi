package com.zhidianfan.pig.yd.moduler.manage.feign;

import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.yd.moduler.manage.dto.TipCommon;
import com.zhidianfan.pig.yd.moduler.manage.dto.UserDTO;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/27
 * @Modified By:
 */

@Service
public class AuthFeignFactory implements FallbackFactory<AuthFeign> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public AuthFeign create(Throwable throwable) {
        log.info(throwable.getMessage());
        return new AuthFeign() {
            @Override
            public Tip createUserAdmin(UserDTO userDTO) {
                log.error("调用 createUserAdmin 异常");
                return new ErrorTip(4001,"调用 createUserAdmin 异常");
            }

            @Override
            public SuccessTip createUser(UserDTO userDTO) {
                log.error("调用 createUser 异常");
                return  new SuccessTip(4001,"调用 createUser 异常");
            }

            @Override
            public int findUserNum(String username, String clientId) {
                log.error("调用 findUserNum 异常");
                return  -1;
            }

            @Override
            public SuccessTip updateUser(UserDTO userDTO) {
                log.error("调用 updateUser 异常");
                return  new SuccessTip(4001,"调用 updateUser 异常");
            }

            @Override
            public SuccessTip userFindExist(String mobile) {
                log.error("调用 updateUser 异常");
                return  new SuccessTip(4001,"调用 updateUser 异常");
            }

            @Override
            public SuccessTip updateUserPassword(String mobile, String password) {
                log.error("调用 updateUserPassword 异常");
                return  new SuccessTip(4001,"调用 updateUserPassword 异常");
            }

            @Override
            public SuccessTip updateUserPasswordAccount(String account,String password,String oldPassword) {
                log.error("调用 updateUserPasswordAccount 异常");
                return  new SuccessTip(4001,"调用 updateUserPasswordAccount 异常");
            }

            @Override
            public SuccessTip deleteUser(String username, String clientId) {
                log.error("调用 deleteUser 异常");
                return  new SuccessTip(4001,"调用 deleteUser 异常");
            }

            @Override
            public SuccessTip addUserRole(String username, String roleName) {
                log.error("调用 addUserRole 异常");
                return new SuccessTip(4001,"调用 addUserRole 异常");
            }

            @Override
            public SuccessTip delUserRole(String username, String roleName) {
                log.error("调用 delUserRole 异常");
                return new SuccessTip(4001,"调用 delUserRole 异常");
            }

            @Override
            public TipCommon checkAuth(String username, String password, String clientType) {
                log.error("调用 UserService.checkAuth 异常：{}，{},{}", username, password, clientType);
                return new TipCommon(500, throwable.getMessage());
            }

        };
    }
}
