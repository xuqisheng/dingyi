package com.zhidianfan.pig.yd.moduler.resv.service;

import com.zhidianfan.pig.common.vo.UserVO;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.SysUserFeign;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.UserDTO;
import com.zhidianfan.pig.yd.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 多任务处理
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/28
 * @Modified By:
 */

public class UserAuthRunn implements Runnable {

    private Logger log = LoggerFactory.getLogger(getClass());

    private SysUserFeign sysUserFeign;

    private List<Integer> role;
    private Integer deptId;
    private List<Map<String, String>> userMapList;


    public UserAuthRunn(SysUserFeign sysUserFeign, List<Integer> role, Integer deptId, List<Map<String, String>> userMapList) {
        this.sysUserFeign = sysUserFeign;
        this.role = role;
        this.deptId = deptId;
        this.userMapList = userMapList;
    }

    @Override
    public void run() {
        try {
            this.makeUserAuth();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    private void makeUserAuth() {

        for (Map<String, String> map : userMapList) {
            String username = map.get("username");
            String password = map.get("password");
            String phone = map.get("phone");
            //用户是否存在
            UserVO userVO = sysUserFeign.findUserByUsername(username);
            //密码是否一致
            if (userVO != null) {
                String p1 = userVO.getPassword();
                boolean b = PasswordUtils.matches(password, p1);
                if (!b) {
                    //更新
                    UserVO userVO1 = new UserVO();
                    userVO1.setUsername(username);

                    UserDTO userDTO = new UserDTO();
                    userDTO.setNewpassword1(password);//新密码
                    userDTO.setPassword(p1);//旧表中的旧密码
                    sysUserFeign.editInfo(userDTO, userVO1);
                } else {
                    log.info("用户认证信息无变化，无需变更");
                }
            } else {
                //插入
                UserDTO userDTO = new UserDTO();
                userDTO.setRole(role);//角色：普通酒店客户
                userDTO.setDeptId(deptId);//部门：客户-普通酒店
                userDTO.setUsername(username);
                userDTO.setNewpassword1(password);
                userDTO.setPhone(phone);//手机号
                userDTO.setCreateTime(new Date());

                sysUserFeign.user(userDTO);
                log.info("新增用户认证信息：{}", userDTO);
            }
        }

    }
}
