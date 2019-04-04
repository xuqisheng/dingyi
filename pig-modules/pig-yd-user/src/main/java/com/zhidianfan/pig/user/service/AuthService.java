package com.zhidianfan.pig.user.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.user.dao.entity.YdRole;
import com.zhidianfan.pig.user.dao.entity.YdUser;
import com.zhidianfan.pig.user.dao.entity.YdUserRole;
import com.zhidianfan.pig.user.dao.service.IYdRoleService;
import com.zhidianfan.pig.user.dao.service.IYdUserRoleService;
import com.zhidianfan.pig.user.dao.service.IYdUserService;
import com.zhidianfan.pig.user.dto.UserDTO;
import com.zhidianfan.pig.user.utils.IgnorePropertiesUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import sun.rmi.runtime.Log;

import java.util.Date;
import java.util.List;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/11/7
 * @Modified By:
 */
@Service
public class AuthService {

    @Autowired
    private IYdUserService ydUserService;

    @Autowired
    private IYdRoleService ydRoleService;

    @Autowired
    private IYdUserRoleService ydUserRoleService;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

//    @Cacheable(value = "yiding_user_AuthService", key = "'checkAuth:'+#username+#password+#clientType")
//存在缓存，直接获取缓存，不存在缓存，查询后加入缓存
    public boolean checkAuth(String username, String password, String clientType) {
        int count = ydUserService.selectCount(new EntityWrapper<YdUser>()
                .eq("client_id", clientType)
                .eq("username", username)
                .eq("password", password)
                .eq("del_flag", "0"));
        return count > 0;
    }

    /**
     * 检查用户名与url匹配性
     *
     * @param username
     * @param url
     * @return
     */
//    @Cacheable(value = "yiding_user_AuthService", key = "'checkUsernameUrl:'+#username+#url")//存在缓存，直接获取缓存，不存在缓存，查询后加入缓存
    public boolean checkUsernameUrl(String username, String url) {
        //检查该用户名拥有的角色
        List<YdUserRole> roles = ydUserRoleService.selectList(new EntityWrapper<YdUserRole>()
                .eq("username", username)
                .eq("is_use", 1));
        boolean f1 = false;
        //判断下面的所有角色
        for (YdUserRole role : roles) {
            List<YdRole> list = ydRoleService.selectList(new EntityWrapper<YdRole>()
                    .eq("role_name", role.getRoleName()));
            for (YdRole role1 : list) {
                if (1 == role1.getFlag()) {//优先否决
                    boolean b = pathMatcher.match(role1.getAntUrl(), url);
                    if (b) {//只要任意一个角色，符合否决条件，就拒绝访问
                        return false;
                    }
                } else {
                    boolean b = pathMatcher.match(role1.getAntUrl(), url);
                    if (b) {
                        f1 = true;
                    }
                }
            }
        }


        return f1;
    }

    /**
     * 检查角色名与url匹配性
     *
     * @param role
     * @param url
     * @return
     */
    @Cacheable(value = "yiding_user_AuthService", key = "'checkRoleUrl:'+#role+#url")//存在缓存，直接获取缓存，不存在缓存，查询后加入缓存
    public boolean checkRoleUrl(String role, String url) {

        boolean f1 = false;
        //判断角色
        List<YdRole> list = ydRoleService.selectList(new EntityWrapper<YdRole>()
                .eq("role_name", role));
        for (YdRole role1 : list) {
            if (1 == role1.getFlag()) {//优先否决
                boolean b = pathMatcher.match(role1.getAntUrl(), url);
                if (b) {//只要任意一个角色，符合否决条件，就拒绝访问
                    return false;
                }
            } else {
                boolean b = pathMatcher.match(role1.getAntUrl(), url);
                if (b) {
                    f1 = true;
                }
            }
        }
        return f1;
    }

    @Cacheable(value = "yiding_user_AuthService", key = "'checkAuth:'+#username+#clientType")
    public boolean checkAuthV2(String username, String clientType) {
        int count = ydUserService.selectCount(new EntityWrapper<YdUser>()
                .eq("client_id", clientType)
                .eq("username", username)
                .eq("del_flag", "0"));
        return count > 0;
    }


    public Tip createAdminUser(UserDTO userDTO){
        userDTO.setRoleName("admin");
        return createUser(userDTO);
    }


    @Transactional(rollbackFor = Exception.class)
    public Tip createUser(UserDTO userDTO){

        //判断账号和clientId是否唯一
        YdUser condition = new YdUser();
        condition.setClientId(userDTO.getClientId());
        condition.setUsername(userDTO.getUsername());
        YdUser one = ydUserService.selectOne(new EntityWrapper<>(condition));
        if(one!=null){
            if(one.getDelFlag().equals("0"))
                return new SuccessTip(4001,"账号重复");
            else
                ydUserService.deleteById(one.getId());
        }

        YdUser ydUser = new YdUser();
        BeanUtils.copyProperties(userDTO,ydUser);
        ydUser.setCreateTime(new Date());
        boolean userStatus = ydUserService.insert(ydUser);
        boolean userRoleStatu = installUserRole(userDTO.getUsername(),userDTO.getRoleName());
        if(userStatus && userRoleStatu){
            return new SuccessTip(200,"修改成功");
        }else {
            return new SuccessTip(4001,"修改失败");
        }
    }

    public Tip updateUser(UserDTO userDTO){
        YdUser condition = new YdUser();
        condition.setClientId(userDTO.getClientId());
        condition.setUsername(userDTO.getUsername());
        YdUser ydUser = ydUserService.selectOne(new EntityWrapper<>(condition));
        if(ydUser==null){
            return new SuccessTip(4001,"修改失败");
        }

        BeanUtils.copyProperties(userDTO,ydUser, IgnorePropertiesUtil.getNullPropertyNames(userDTO));
        ydUser.setUpdateTime(new Date());
        boolean userStatus = ydUserService.updateById(ydUser);
        if(userStatus ){
            return new SuccessTip(200,"修改成功");
        }else {
            return new SuccessTip(4001,"修改失败");
        }
    }


    public Tip updatePasswordUser(String mobile,String password){
        YdUser condition = new YdUser();
        condition.setPhone(mobile);
        condition.setDelFlag("0");

        YdUser ydUser = new YdUser();
        ydUser.setPassword(password);
        ydUser.setUpdateTime(new Date());
        boolean userStatus = ydUserService.update(ydUser,new EntityWrapper<>(condition));
        if(userStatus ){
            return new SuccessTip(200,"修改成功");
        }else {
            return new SuccessTip(4001,"修改失败");
        }
    }

    public boolean installUserRole(String userName,String roleName) {

        YdUserRole ydUserRole = new YdUserRole();
        ydUserRole.setUsername(userName);
        ydUserRole.setRoleName(roleName);
        ydUserRole.setIsUse(1);

        YdUserRole one = ydUserRoleService.selectOne(new EntityWrapper<>(ydUserRole));
        if(one!=null){
           return  true;
        }
        return ydUserRoleService.insert(ydUserRole);
    }



    public boolean delUserRole(  String username,  String roleName) {
        YdUserRole condition = new YdUserRole();
        condition.setRoleName(roleName);
        condition.setUsername(username);

        YdUserRole newYdUserRole = new YdUserRole();
        newYdUserRole.setIsUse(0);
        return ydUserRoleService.update(newYdUserRole, new EntityWrapper<>(condition));
    }


    public boolean deleteUser(String username,  String clientId) {
        YdUser condition = new YdUser();
        condition.setClientId(clientId);
        condition.setUsername(username);
        boolean delete = ydUserService.delete(new EntityWrapper<>(condition));
        //（暂不处理！！）   判断yd_user此用户名是否只有一条
//        YdUser usernameOne = new YdUser();
//        usernameOne.setUsername(username);
//        int i = ydUserService.selectCount(new EntityWrapper<>(usernameOne));
//        if(i==1){
//            //只有一条 删除userRole  因为userRole只和名字对应
//            YdUserRole ydUserRole = new YdUserRole();
//            ydUserRole.setUsername();
//            ydUserRoleService.delete()
//        }

        return delete;
    }


    public Tip userFindExist(String mobile) {

        YdUser ydUser = new YdUser();
        ydUser.setPhone(mobile);
        ydUser.setDelFlag("0");
        int i = ydUserService.selectCount(new EntityWrapper<>(ydUser));
        if(i>0){
            return SuccessTip.SUCCESS_TIP;
        }else {
            return ErrorTip.ERROR_TIP;
        }

    }

    public YdUser findUser(String username, String clientType) {
        YdUser ydUser = ydUserService.selectOne(new EntityWrapper<YdUser>().eq("username", username).eq("client_id", clientType));
        return ydUser;
    }
}
