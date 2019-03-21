package com.zhidianfan.pig.yd.moduler.resv.service.rmi.fallback;

import com.zhidianfan.pig.common.util.R;
import com.zhidianfan.pig.common.vo.UserVO;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.SysUserFeign;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.UserDTO;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/28
 * @Modified By:
 */
@Component
public class SysUserFeignFactory implements FallbackFactory<SysUserFeign> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public SysUserFeign create(Throwable throwable) {
        log.error("SysUserFeign调用异常：{}", throwable.getMessage());
        return new SysUserFeign() {
            @Override
            public UserVO findUserByUsername(String username) {
                log.error("调用异常 {}.{} ", "SysUserFeignImpl", "findUserByUsername");
                return null;
            }

            @Override
            public R<Boolean> userDel(Integer id) {
                log.error("调用异常 {}.{} ", "SysUserFeignImpl", "userDel");
                return null;
            }

            @Override
            public R<Boolean> user(UserDTO userDto) {
                log.error("调用异常 {}.{} ", "SysUserFeignImpl", "user");
                return null;
            }

            @Override
            public R<Boolean> userUpdate(UserDTO userDto) {
                log.error("调用异常 {}.{} ", "SysUserFeignImpl", "userUpdate");
                return null;
            }

            @Override
            public R<Boolean> editInfo(UserDTO userDto, UserVO userVo) {
                log.error("调用异常 {}.{} ", "SysUserFeignImpl", "editInfo");
                return null;
            }
        };
    }
}
