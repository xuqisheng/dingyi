package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.util.R;
import com.zhidianfan.pig.common.vo.UserVO;
import com.zhidianfan.pig.yd.moduler.common.constant.ClientId;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.SysDeptFeign;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.SysRoleFeign;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.SysUserFeign;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.DeptDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.RoleDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.SysDept;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.UserDTO;
import com.zhidianfan.pig.yd.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/28
 * @Modified By:
 */
@Service
@Slf4j
public class UserAuthService {

    //手机、web
    @Autowired
    private IAppUserService appUserService;
    //集团管理
    @Autowired
    private IBrandUserService brandUserService;
    //pad
    @Autowired
    private IDeviceUserService deviceUserService;
    //门店后台
    @Autowired
    private ISellerUserService sellerUserService;
    //总后台
    @Autowired
    private IXtUserService xtUserService;

    //角色
    @Autowired
    private RoleService roleService;
    //部门
    @Autowired
    private DeptService deptService;
    @Autowired
    private SysUserFeign sysUserFeign;
    @Autowired
    private SysRoleFeign sysRoleFeign;
    @Autowired
    private SysDeptFeign sysDeptFeign;
    //合计处理
    private int count = 0;

    @Async
    public void doAuthSync() {
        count = 0;
        long s = System.currentTimeMillis();
//        ExecutorService executorService = Executors.newFixedThreadPool(50);
        Page page = new Page(1, 100);
        //处理手机、web
        for (; ; ) {
            //app_user的有效用户
            Page<AppUser> appUserPage = appUserService.selectPage(page, new EntityWrapper<AppUser>()
                    .eq("status", "1"));
            if (appUserPage == null
                    || CollectionUtils.isEmpty(appUserPage.getRecords())) {
                break;
            } else {
                List<AppUser> appUserList = appUserPage.getRecords();

                appUserList.stream()
                        .forEach(appUser -> {

                                    String password = appUser.getAppUserPassword();
                                    this.regUser(appUser.getAppUserPhone(), getOldPassword(), password, appUser.getAppUserPhone(), ClientId.APP.name());
                                }

                        );

//                List<Map<String, String>> list1 = appUserList.stream()
//                        .map(appUser -> {
//                            Map<String, String> map = new HashMap<>();
//                            String username = appUser.getAppUserPhone();
//                            String password = appUser.getAppUserPassword();
//                            String phone = appUser.getAppUserPhone();
//                            map.put("username", username);
//                            map.put("password", password);
//                            map.put("phone", phone);
//
//                            return map;
//                        }).collect(Collectors.toList());
//                executorService.execute(new UserAuthRunn(sysUserFeign, Arrays.asList(21), 14, list1));

//                web用户与app用户来自于一张表，不再区分
//                List<Map<String, String>> list2 = appUserList.stream()
//                        .map(appUser -> {
//                            Map<String, String> map = new HashMap<>();
//                            String username = appUser.getAppUserPhone();
//                            String password = appUser.getAppUserPassword();
//                            String phone = appUser.getAppUserPhone();
//                            map.put("username", username);
//                            map.put("password", password);
//                            map.put("phone", phone);
//
//                            return map;
//                        }).collect(Collectors.toList());
//                executorService.execute(new UserAuthRunn(sysUserFeign, Arrays.asList(21), 14, list2));

                page.setCurrent(page.getCurrent() + 1);
            }
        }
        page.setCurrent(1);
        //处理集团管理
        for (; ; ) {
            Page<BrandUser> brandUserPage = brandUserService.selectPage(page);
            if (brandUserPage == null || CollectionUtils.isEmpty(brandUserPage.getRecords())) {
                break;
            } else {
                List<BrandUser> brandUsers = brandUserPage.getRecords();
                brandUsers.stream()
                        .forEach(user -> {
                                    String username = user.getUsername();
//                                    String password = user.getPassword();
                                    String password = user.getPassword();
                                    String phone = "";
                                    if (username.length() == 11) {
                                        phone = username;
                                    }
                                    this.regUser(username, getOldPassword(), password, phone, ClientId.BRAND.name());
                                }
                        );

//                List<Map<String, String>> list1 = brandUsers.stream()
//                        .map(user -> {
//                            Map<String, String> map = new HashMap<>();
//                            String username = user.getUsername();
//                            String password = user.getPassword();
//                            String phone = "";
//                            if (username.length() == 11) {
//                                phone = username;
//                            }
//                            map.put("username", username);
//                            map.put("password", password);
//                            map.put("phone", phone);
//
//                            return map;
//                        }).collect(Collectors.toList());
//                executorService.execute(new UserAuthRunn(sysUserFeign, Arrays.asList(21), 14, list1));

                page.setCurrent(page.getCurrent() + 1);
            }
        }
        page.setCurrent(1);
        //处理pad
        for (; ; ) {
            Page<DeviceUser> deviceUserPage = deviceUserService.selectPage(page, new EntityWrapper<DeviceUser>()
                    .eq("status", "1"));
            if (deviceUserPage == null
                    || CollectionUtils.isEmpty(deviceUserPage.getRecords())) {
                break;
            } else {
                List<DeviceUser> deviceUsers = deviceUserPage.getRecords();
                deviceUsers.stream()
                        .forEach(user -> {
                                    String username = user.getDeviceUserPhone();
//                                    String password = user.getDeviceUserPassword();
                                    String password = user.getDeviceUserPassword();
                                    String phone = "";
                                    if (username.length() == 11) {
                                        phone = username;
                                    }
                                    this.regUser(username, getOldPassword(), password, phone, ClientId.PAD.name());
                                }
                        );
//                List<Map<String, String>> list1 = deviceUsers.stream()
//                        .map(user -> {
//                            Map<String, String> map = new HashMap<>();
//                            String username = user.getDeviceUserPhone();
//                            String password = user.getDeviceUserPassword();
//                            String phone = "";
//                            if (username.length() == 11) {
//                                phone = username;
//                            }
//                            map.put("username", username);
//                            map.put("password", password);
//                            map.put("phone", phone);
//
//                            return map;
//                        }).collect(Collectors.toList());
//                executorService.execute(new UserAuthRunn(sysUserFeign, Arrays.asList(21), 14, list1));

                page.setCurrent(page.getCurrent() + 1);
            }
        }
        page.setCurrent(1);
        //处理门店后台
        for (; ; ) {
            Page<SellerUser> sellerUserPage = sellerUserService.selectPage(page);
            if (sellerUserPage == null
                    || CollectionUtils.isEmpty(sellerUserPage.getRecords())) {
                break;
            } else {
                List<SellerUser> sellerUsers = sellerUserPage.getRecords();
                sellerUsers.stream()
                        .forEach(user -> {
                                    String username = user.getLoginName();
//                                    String password = user.getPassword();
                                    String password = user.getPassword();
                                    String phone = "";
                                    if (username.length() == 11) {
                                        phone = username;
                                    }
                                    this.regUser(username, getOldPassword(), password, phone, ClientId.SELLER.name());
                                }
                        );
//                List<Map<String, String>> list1 = sellerUsers.stream()
//                        .map(user -> {
//                            Map<String, String> map = new HashMap<>();
//                            String username = user.getLoginName();
//                            String password = user.getPassword();
//                            String phone = "";
//                            if (username.length() == 11) {
//                                phone = username;
//                            }
//                            map.put("username", username);
//                            map.put("password", password);
//                            map.put("phone", phone);
//
//                            return map;
//                        }).collect(Collectors.toList());
//                executorService.execute(new UserAuthRunn(sysUserFeign, Arrays.asList(21), 14, list1));

                page.setCurrent(page.getCurrent() + 1);
            }
        }
        page.setCurrent(1);
        //处理总后台
        for (; ; ) {
            Page<XtUser> xtUserPage = xtUserService.selectPage(page);
            if (xtUserPage == null
                    || CollectionUtils.isEmpty(xtUserPage.getRecords())) {
                break;
            } else {
                List<XtUser> xtUsers = xtUserPage.getRecords();
                xtUsers.stream()
                        .forEach(user -> {
                                    String username = user.getLoginName();
//                                    String password = user.getPassword();
                                    String password = user.getPassword();
                                    String phone = "";
                                    if (username.length() == 11) {
                                        phone = username;
                                    }
                                    this.regUser(username, getOldPassword(), password, phone, ClientId.XT.name());
                                }
                        );
//                List<Map<String, String>> list1 = xtUsers.stream()
//                        .map(user -> {
//                            Map<String, String> map = new HashMap<>();
//                            String username = user.getLoginName();
//                            String password = user.getPassword();
//                            String phone = "";
//                            if (username.length() == 11) {
//                                phone = username;
//                            }
//                            map.put("username", username);
//                            map.put("password", password);
//                            map.put("phone", phone);
//
//                            return map;
//                        }).collect(Collectors.toList());
//                executorService.execute(new UserAuthRunn(sysUserFeign, Arrays.asList(22), 10, list1));

                page.setCurrent(page.getCurrent() + 1);

            }
        }

//        executorService.shutdown();
        long e = System.currentTimeMillis();
        log.info("处理客户：{} ,耗时：{} 毫秒", count, (e - s));
    }

    private String getOldPassword() {

        String sb = "";
        for (int i = 0; i < 60; i++) {
            sb += "1";
        }
        return sb;
    }

    /**
     * 用户注册
     *
     * @param username
     * @param oldPasword
     * @param password
     * @param phone
     * @param clientId
     * @return
     */
    public Tip regUser(String username, String oldPasword, String password, String phone, String clientId) {

        UserVO userVO = sysUserFeign.findUserByUsername(username);
        Tip tip = null;
        if (userVO != null) {
            String p1 = userVO.getPassword();//旧密码
            boolean b = PasswordUtils.matches(password, p1);
            if (!b) {
                //更新
                UserVO userVO1 = new UserVO();
                userVO1.setUsername(username);

                UserDTO userDTO = new UserDTO();
                userDTO.setNewpassword1(password);//新密码
                userDTO.setPassword(oldPasword);//旧表中的旧密码
                userDTO.setUsername(username);
                R<Boolean> r = sysUserFeign.editInfo(userDTO, userVO1);
                if (r != null && r.getData()) {
                    tip = new SuccessTip(200, "密码已修改");
                } else {
                    tip = new SuccessTip(500, "密码修改失败");
                }

            } else {
                tip = new SuccessTip(200, "用户认证信息无变化，无需变更");
            }
        } else {
            //插入
            UserDTO userDTO = new UserDTO();
            userDTO.setRole(Arrays.asList(21));//角色：普通酒店客户
            userDTO.setDeptId(14);//部门：客户-普通酒店
            userDTO.setUsername(username);
            userDTO.setNewpassword1(password);
            userDTO.setPhone(phone);//手机号
            userDTO.setCreateTime(new Date());

            R<Boolean> r = sysUserFeign.user(userDTO);
            if (r != null && r.getData()) {
                tip = new SuccessTip(200, "新增用户认证信息-成功");
            } else {
                tip = new SuccessTip(500, "新增用户认证信息-失败");
            }
        }

//        try {
//            TimeUnit.MILLISECONDS.sleep(50);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        log.info("处理 第 {} 条 ： {} , {}\t\t{}", (count++), clientId, username, tip.getCode() + ":" + tip.getMsg());
        return tip;
    }

    /**
     * 用户注册V2
     *
     * @param username
     * @param oldPasword
     * @param password
     * @param phone
     * @param clientId
     * @param roles
     * @param deptName
     * @param parentDeptName
     * @return
     */
    public Tip regUserV2(String username, String oldPasword, String password, String phone,
                         String clientId, String roles, String deptName, String parentDeptName) {

        UserVO userVO = sysUserFeign.findUserByUsername(username);

        //获得部门信息
        SysDept sysDept = sysDeptFeign.findDeptByDeptname(deptName);
        if (sysDept == null) {
            Tip tip = deptService.regDept(new DeptDTO.Builder().name(deptName).partneName(parentDeptName).delFlag("0").build());
            if (tip.getCode() != 200) return tip;
            sysDept = sysDeptFeign.findDeptByDeptname(deptName);
        }

        String[] roleArr = roles.split(",");
        List<Integer> roleIdList = new LinkedList<>();
        //遍历角色，如果角色不存在则新增
        for (int i = 0; i < roleArr.length; i++) {
            if (StringUtils.isEmpty(roleArr[i])) {
                continue;
            }
            // roleCode:roleName:roleDesc:delFlag:deptName
            String[] roleStrArr = roleArr[i].split(":", -1);
            RoleDTO roleDTO = sysRoleFeign.findRoleByRolecode(roleStrArr[0]);
            if (roleDTO == null) {
                RoleDTO role = new RoleDTO.Builder().roleCode(roleStrArr[0]).roleName(roleStrArr[1]).roleDesc(roleStrArr[2]).delFlag(roleStrArr[3]).deptName(roleStrArr[4]).parentDeptName(roleStrArr[5]).build();
                roleService.regRole(role);
                roleDTO = sysRoleFeign.findRoleByRolecode(roleStrArr[0]);
            }
            if (!roleIdList.contains(roleDTO.getRoleId())) roleIdList.add(roleDTO.getRoleId());
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);

        Tip tip = null;

        if (userVO != null) {
            //更新
            String p1 = userVO.getPassword();//旧密码
            boolean b = PasswordUtils.matches(password, p1);
            if (!b) {
                //更新
                UserVO userVO1 = new UserVO();
                userVO1.setUsername(username);

                userDTO.setNewpassword1(password);//新密码
                userDTO.setPassword(oldPasword);//旧表中的旧密码

                R<Boolean> r = sysUserFeign.editInfo(userDTO, userVO1);
                if (r != null && r.getData()) {
                    tip = new SuccessTip(200, "密码已修改");
                } else {
                    tip = new SuccessTip(500, "密码修改失败");
                }
                userVO = sysUserFeign.findUserByUsername(username);
            }
            BeanUtils.copyProperties(userVO, userDTO);
//            userDTO.setPassword(userVO.getPassword());
//            userDTO.setUserId(userVO.getUserId());
            userDTO.setUpdateTime(new Date());
            userDTO.setDeptId(sysDept.getDeptId());
            userDTO.setRole(roleIdList);

            R<Boolean> r = sysUserFeign.userUpdate(userDTO);
            if (r != null && r.getData()) {
                tip = new SuccessTip(200, "用户修改成功");
            } else {
                tip = new SuccessTip(500, "用户修改失败");
            }
        } else {
            //插入
            userDTO.setPhone(phone);//手机号
            userDTO.setDeptId(sysDept.getDeptId());
            userDTO.setRole(roleIdList);
            userDTO.setNewpassword1(password);
            userDTO.setCreateTime(new Date());

            R<Boolean> r = sysUserFeign.user(userDTO);
            if (r != null && r.getData()) {
                tip = new SuccessTip(200, "新增用户认证信息-成功");
            } else {
                tip = new SuccessTip(500, "新增用户认证信息-失败");
            }
        }

        log.info("处理 第 {} 条 ： {} , {}\t\t{}", (count++), clientId, username, tip.getCode() + ":" + tip.getMsg());
        return tip;
    }

    /**
     * 判断用户是否存在
     *
     * @param username
     * @return
     */
    public Boolean existsUser(String username) {
        UserVO userVO = sysUserFeign.findUserByUsername(username);
        return userVO != null;
    }


    public UserVO findUser(String username) {
        UserVO userVO = sysUserFeign.findUserByUsername(username);
        return userVO;
    }

    /**
     * 删除用户
     *
     * @param username
     * @return
     */
    public Boolean delUser(String username) {
        UserVO userVO = sysUserFeign.findUserByUsername(username);
        if (userVO == null) return true;
        R<Boolean> r = sysUserFeign.userDel(userVO.getUserId());
        return r.getData();
    }
}
