package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.vo.SysRole;
import com.zhidianfan.pig.common.vo.UserVO;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.manage.dto.UserDTO;
import com.zhidianfan.pig.yd.moduler.manage.feign.AuthFeign;
import com.zhidianfan.pig.yd.moduler.manage.feign.SysDictFeign;
import com.zhidianfan.pig.yd.moduler.resv.bo.BusinessBrandBo;
import com.zhidianfan.pig.yd.moduler.resv.dto.EmployeeDTO;
import com.zhidianfan.pig.yd.utils.PingyinUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: 门店后台员工管理
 * @User: ljh
 * @Date: 2019-01-28
 * @Time: 14:34
 */
@Service
public class BusinessEmployeeService {


    @Autowired
    private IBusinessService businessService;



    @Resource
    private IAndroidUserInfoService iAndroidUserInfoService;

    @Resource
    private ISellerUserService iSellerUserService;

    @Resource
    private ISysSyncUserService iSysSyncUserService;

    @Resource
    private ISysSyncUserBusinessService iSysSyncUserBusinessService;

    @Resource
    private UserAuthService userAuthService;


    @Autowired
    private AuthFeign authFeign;

    @Resource
    private SysDictFeign sysDictFeign;


    @Resource
    private ISmallAppUserService iSmallAppUserService;

    @Autowired
    private IHotelStaffMappingService hotelStaffMappingService;


    @Autowired
    private ISellerGroupUserService iSellerGroupUserService;

    @Autowired
    private ISellerGroupService iSellerGroupService;





    /**
     * 员工添加
     *
     * @param bean
     */
    public void addEmployee(EmployeeDTO bean) {

        Integer businessId = bean.getBusinessId();
        List<String> addDevice = bean.getAddDevice();

        //新增
        Integer userId = addSyncUser(bean);

        //String role="";

        //新增登录设备
        for (String device : addDevice) {
            if ("电话机".equals(device)) {
                //判断账号是否被注册
                existAndroidUserInfoMobile(bean.getAccount());

                AndroidUserInfo androidUserInfo = new AndroidUserInfo();

                BeanUtils.copyProperties(bean, androidUserInfo);
                androidUserInfo.setAppUserPhone(bean.getMobile());
                androidUserInfo.setLoginName(bean.getAccount());
                androidUserInfo.setAppUserPassword(bean.getPassword());
                androidUserInfo.setCreatedAt(new Date());
                iAndroidUserInfoService.insert(androidUserInfo);
                //新增数据 sys_sync_user_business
                addSyncUserBusiness(bean, userId, "ANDROID_PHONE");
                //role+=","+androidPhone.getRole();

                //新增到user模块的权限
                addModelUser(bean,"ANDROID_PHONE");

            } else if ("门店后台".equals(device)) {

                existSellerLoginName(bean.getAccount());

                SellerUser appUser = new SellerUser();

                BeanUtils.copyProperties(bean, appUser);
                appUser.setTelphone(bean.getMobile());
                appUser.setLoginName(bean.getAccount());
                appUser.setName(bean.getUserName());
                appUser.setBusinessId(businessId + "");

                appUser.setCreatedAt(new Date());
                iSellerUserService.insert(appUser);

                SellerUser sellerUser = iSellerUserService.selectOne(new EntityWrapper<SellerUser>().eq("login_name",bean.getAccount()).eq("business_id",businessId + ""));

                hotelStaffMappingService.delete(new EntityWrapper<HotelStaffMapping>().eq("staff_id",String.valueOf(sellerUser.getId())).eq("type",2));

                List<BusinessBrandBo> list = bean.getSellerBusinessList();

                for(BusinessBrandBo businessBrandBo : list){
                    HotelStaffMapping hotelStaffMapping = new HotelStaffMapping();
                    hotelStaffMapping.setHotelId(Long.parseLong(String.valueOf(businessBrandBo.getBusinessId())));
                    hotelStaffMapping.setHotelName(businessBrandBo.getBusinessName());
                    hotelStaffMapping.setType(2);
                    hotelStaffMapping.setStaffId(String.valueOf(sellerUser.getId()));
                    hotelStaffMapping.setCreateTime(new Date());
                    hotelStaffMappingService.insert(hotelStaffMapping);
                }

                //新增数据 sys_sync_user_business
                addSyncUserBusiness(bean, userId, "SELLER");
                //role+=","+seller.getRole();

                addModelUser(bean,"WEB_MANAGER");
            }

            else if ("小程序".equals(device)) {

                existSmallAppLoginName(bean.getAccount());

                SmallAppUser appUser = new SmallAppUser();

                BeanUtils.copyProperties(bean, appUser);
                appUser.setTelphone(bean.getMobile());
                appUser.setLoginName(bean.getAccount());
                appUser.setName(bean.getUserName());
                appUser.setBusinessId(businessId + "");

                appUser.setCreatedAt(new Date());
                iSmallAppUserService.insert(appUser);

                SmallAppUser smallAppUser = iSmallAppUserService.selectOne(new EntityWrapper<SmallAppUser>().eq("login_name",bean.getAccount()).eq("business_id",businessId + ""));

                hotelStaffMappingService.delete(new EntityWrapper<HotelStaffMapping>().eq("staff_id",String.valueOf(smallAppUser.getId())).eq("type",1));

                List<BusinessBrandBo> businessList = bean.getBusinessList();

                for(BusinessBrandBo businessBrandBo : businessList){
                    HotelStaffMapping hotelStaffMapping = new HotelStaffMapping();
                    hotelStaffMapping.setHotelId(Long.parseLong(String.valueOf(businessBrandBo.getBusinessId())));
                    hotelStaffMapping.setHotelName(businessBrandBo.getBusinessName());
                    hotelStaffMapping.setType(1);
                    hotelStaffMapping.setStaffId(String.valueOf(smallAppUser.getId()));
                    hotelStaffMapping.setCreateTime(new Date());
                    hotelStaffMappingService.insert(hotelStaffMapping);
                }

                //新增数据 sys_sync_user_business
                addSyncUserBusiness(bean, userId, "SMALL_APP");

                addModelUser(bean,"SMALL_APP");
            }
        }

    }


    public void addModelUser(EmployeeDTO bean,String clientName) {
        String userType = sysDictFeign.getDict(clientName, "user_type");
        if(StringUtils.isBlank(userType)){
            throw new RuntimeException("无法获取clientId："+userType);
        }

        int userNum = authFeign.findUserNum(bean.getAccount(), userType);
        if(userNum!=0){
            throw new RuntimeException("账号重复");
        }

        UserDTO userDTO = new UserDTO();
        //WEB_MANAGER 门店后台    ANDROID_PHONE  安卓电话机
        userDTO.setClientId(clientName);
        userDTO.setPassword(bean.getPassword());
        userDTO.setPhone(bean.getMobile());
        userDTO.setUsername(bean.getAccount());
        userDTO.setRoleName("admin");
        userDTO.setCreateTime(new Date());

        SuccessTip tip = authFeign.createUser(userDTO);
        if(tip.getCode()!=200){
            //防止账号被占用(所有端)
            authFeign.deleteUser(userDTO.getUsername(),null);
            throw new RuntimeException(tip.getMsg());
        }
    }

    /**
     * user模块 用户编辑
     * @param bean
     * @param clientName
     */
    public void editModelUser(EmployeeDTO bean,String clientName) {
        UserDTO userDTO = new UserDTO();
        //WEB_MANAGER 门店后台    ANDROID_PHONE  安卓电话机
        userDTO.setUsername(bean.getAccount());

        userDTO.setClientId(sysDictFeign.getDict(clientName,"user_type"));
        userDTO.setPassword(bean.getPassword());
        userDTO.setPhone(bean.getMobile());

        authFeign.updateUser(userDTO);
    }


    /**
     * 员工编辑
     * @param bean
     */
    public void editEmployee(EmployeeDTO bean) {

        Integer businessId = bean.getBusinessId();
        List<String> addDevice = bean.getAddDevice();
        List<String> removeDevice = bean.getRemoveDevice();

        SysSyncUser sysSyncUser = editSyncUser(bean);


        if(StringUtils.isBlank(bean.getPassword())){
            bean.setPassword(sysSyncUser.getPassword());
        }

        //新增登录设备
        for (String device : addDevice) {
            if ("电话机".equals(device)) {
                AndroidUserInfo androidUserInfo = new AndroidUserInfo();

                BeanUtils.copyProperties(bean, androidUserInfo);
                androidUserInfo.setAppUserPhone(bean.getMobile());
                androidUserInfo.setLoginName(bean.getAccount());
                //获取密码
                androidUserInfo.setAppUserPassword(bean.getPassword());

                AndroidUserInfo condition = new AndroidUserInfo();
                condition.setLoginName(bean.getAccount());
                condition.setBusinessId(businessId);
                AndroidUserInfo one = iAndroidUserInfoService.selectOne(new EntityWrapper<>(condition));
                if (one == null) {
                    androidUserInfo.setCreatedAt(new Date());
                    //判断手机号码是否被注册
                    existAndroidUserInfoMobile(bean.getAccount());

                    iAndroidUserInfoService.insert(androidUserInfo);
                    addSyncUserBusiness(bean, bean.getId(), "ANDROID_PHONE");
                    addModelUser(bean,"ANDROID_PHONE");
                } else {
                    editModelUser(bean,"ANDROID_PHONE");
                    iAndroidUserInfoService.update(androidUserInfo, new EntityWrapper<>(condition));
                }

            } else if ("门店后台".equals(device)) {

                existSellerLoginName(bean.getAccount());

                SellerUser appUser = new SellerUser();

                BeanUtils.copyProperties(bean, appUser);
                appUser.setTelphone(bean.getMobile());
                appUser.setLoginName(bean.getAccount());
                appUser.setName(bean.getUserName());
                appUser.setBusinessId(businessId + "");
                appUser.setPassword(bean.getPassword());

                SellerUser condition = new SellerUser();
                condition.setLoginName(bean.getAccount());
                condition.setBusinessId(businessId + "");
                SellerUser one = iSellerUserService.selectOne(new EntityWrapper<>(condition));
                if (one == null) {
                    appUser.setCreatedAt(new Date());
                    iSellerUserService.insert(appUser);
                    addModelUser(bean,"WEB_MANAGER");
                    addSyncUserBusiness(bean, bean.getId(), "SELLER");
                } else {
                    editModelUser(bean,"WEB_MANAGER");
                    iSellerUserService.update(appUser, new EntityWrapper<>(condition));
                }
            }

            else if ("小程序".equals(device)) {

                existSmallAppLoginName(bean.getAccount());

                SmallAppUser appUser = new SmallAppUser();

                BeanUtils.copyProperties(bean, appUser);
                appUser.setTelphone(bean.getMobile());
                appUser.setLoginName(bean.getAccount());
                appUser.setName(bean.getUserName());
                appUser.setBusinessId(businessId + "");
                appUser.setPassword(bean.getPassword());

                SmallAppUser condition = new SmallAppUser();
                condition.setLoginName(bean.getAccount());
                condition.setBusinessId(businessId + "");
                SmallAppUser one = iSmallAppUserService.selectOne(new EntityWrapper<>(condition));
                if (one == null) {
                    appUser.setCreatedAt(new Date());
                    iSmallAppUserService.insert(appUser);
                    addModelUser(bean,"SMALL_APP");
                    addSyncUserBusiness(bean, bean.getId(), "SMALL_APP");
                } else {
                    editModelUser(bean,"SMALL_APP");
                    iSmallAppUserService.update(appUser, new EntityWrapper<>(condition));
                }

            }
        }

        //移除登录设备
        for (String device : removeDevice) {
            if ("电话机".equals(device)) {
                //删除电话机表
                AndroidUserInfo androidUserInfo = new AndroidUserInfo();
                androidUserInfo.setLoginName(bean.getAccount());
                androidUserInfo.setBusinessId(businessId);
                iAndroidUserInfoService.delete(new EntityWrapper<>(androidUserInfo));

                //delPigRole(bean.getAccount(), sysSyncUserBusiness.getRole());
                //删除SyncUserBusiness记录
                delSyncUserBusiness(businessId, bean.getId(), "ANDROID_PHONE");


                //移除user模块用户
                authFeign.deleteUser(bean.getAccount(),"ANDROID_PHONE");

            } else if ("门店后台".equals(device)) {
                //基础seller表记录
                SellerUser appUser = new SellerUser();
                appUser.setLoginName(bean.getAccount());
                appUser.setBusinessId(businessId + "");
                iSellerUserService.delete(new EntityWrapper<>(appUser));

                SysSyncUserBusiness sysSyncUserBusiness = getSysSyncUserBusiness(bean,"SELLER");
                if(sysSyncUserBusiness==null){
                    continue;
                }

                //delPigRole(bean.getAccount(), sysSyncUserBusiness.getRole());

                //移除delSyncUserBusiness记录
                delSyncUserBusiness(businessId, bean.getId(), "SELLER");

                //移除user模块用户
                authFeign.deleteUser(bean.getAccount(),"WEB_MANAGER");

            }

            else if ("小程序".equals(device)) {
                //基础seller表记录
                SmallAppUser appUser = new SmallAppUser();
                appUser.setLoginName(bean.getAccount());
                appUser.setBusinessId(businessId + "");
                iSmallAppUserService.delete(new EntityWrapper<>(appUser));

                SysSyncUserBusiness sysSyncUserBusiness = getSysSyncUserBusiness(bean,"SMALL_APP");
                if(sysSyncUserBusiness==null){
                    continue;
                }

                //移除delSyncUserBusiness记录
                delSyncUserBusiness(businessId, bean.getId(), "SMALL_APP");

                //移除user模块用户
                authFeign.deleteUser(bean.getAccount(),"SMALL_APP");
            }
        }

        SmallAppUser smallAppUser = iSmallAppUserService.selectOne(new EntityWrapper<SmallAppUser>().eq("login_name",bean.getAccount()).eq("business_id",businessId + ""));

        if(smallAppUser != null){
            hotelStaffMappingService.delete(new EntityWrapper<HotelStaffMapping>().eq("staff_id",String.valueOf(smallAppUser.getId())).eq("type",1));

            List<BusinessBrandBo> businessList = bean.getBusinessList();

            for(BusinessBrandBo businessBrandBo : businessList){
                HotelStaffMapping hotelStaffMapping = new HotelStaffMapping();
                hotelStaffMapping.setHotelId(Long.parseLong(String.valueOf(businessBrandBo.getBusinessId())));
                hotelStaffMapping.setHotelName(businessBrandBo.getBusinessName());
                hotelStaffMapping.setType(1);
                hotelStaffMapping.setStaffId(String.valueOf(smallAppUser.getId()));
                hotelStaffMapping.setCreateTime(new Date());
                hotelStaffMappingService.insert(hotelStaffMapping);
            }
        }

        SellerUser sellerUser = iSellerUserService.selectOne(new EntityWrapper<SellerUser>().eq("login_name",bean.getAccount()).eq("business_id",businessId + ""));

        if(sellerUser != null){
            hotelStaffMappingService.delete(new EntityWrapper<HotelStaffMapping>().eq("staff_id",String.valueOf(sellerUser.getId())).eq("type",2));

            List<BusinessBrandBo> list = bean.getSellerBusinessList();

            for(BusinessBrandBo brandBo : list){
                HotelStaffMapping hotelStaffMapping = new HotelStaffMapping();
                hotelStaffMapping.setHotelId(Long.parseLong(String.valueOf(brandBo.getBusinessId())));
                hotelStaffMapping.setHotelName(brandBo.getBusinessName());
                hotelStaffMapping.setType(2);
                hotelStaffMapping.setStaffId(String.valueOf(sellerUser.getId()));
                hotelStaffMapping.setCreateTime(new Date());
                hotelStaffMappingService.insert(hotelStaffMapping);
            }
        }

    }

    /**
     * 判断号码唯一
     * @param loginName
     */
    public void existAndroidUserInfoMobile(String loginName) {

        AndroidUserInfo mobile = new AndroidUserInfo();
        mobile.setLoginName(loginName);
        mobile.setStatus("1");
        int i = iAndroidUserInfoService.selectCount(new EntityWrapper<>(mobile));
        if(i>0){
            throw new RuntimeException("电话机中此账号已被注册");
        }
    }


    public void existSellerLoginName(String account) {

        SellerUser loginName = new SellerUser();
        loginName.setLoginName(account);
        int i = iSellerUserService.selectCount(new EntityWrapper<>(loginName));
        if(i>0){
            throw new RuntimeException("门店后台中此账号已被注册");
        }
    }


    public void existSmallAppLoginName(String account) {

        SmallAppUser loginName = new SmallAppUser();
        loginName.setLoginName(account);
        int i = iSmallAppUserService.selectCount(new EntityWrapper<>(loginName));
        if(i>0){
            throw new RuntimeException("小程序中此账号已被注册");
        }
    }

    /**
     * 删除pig role
     * @param account
     * @param role
     */
    public void delPigRole(String account, String role) {
        role = role.split(":")[0];

        UserVO user = userAuthService.findUser(account);
        if (user == null) {
            return ;
        }
        List<SysRole> roleList = user.getRoleList();
        String finalRole = role;
        Optional<SysRole> first = roleList.stream().filter(item -> item.getRoleCode().equals(finalRole)).findFirst();
        if (first.isPresent()) {
            SysRole sysRole = first.get();
            userAuthService.delUser(sysRole.getRoleName());
        }

    }

    public SysSyncUserBusiness getSysSyncUserBusiness(EmployeeDTO bean, String client) {

        SysSyncUserBusiness condition = new SysSyncUserBusiness();
        condition.setBusinessId(bean.getBusinessId());
        condition.setUserId( bean.getId());
        condition.setClientId(client);
        return iSysSyncUserBusinessService.selectOne(new EntityWrapper<>(condition));
    }

    /**
     * 新增  SyncUserBusiness
     * @param bean
     * @param userId
     * @param client
     * @return
     */
    public SysSyncUserBusiness addSyncUserBusiness(EmployeeDTO bean, Integer userId, String client) {
        Integer businessId = bean.getBusinessId();
        SysSyncUserBusiness sysSyncUserBusiness = new SysSyncUserBusiness();
        sysSyncUserBusiness.setBusinessId(businessId);
        sysSyncUserBusiness.setClientId(client);
        sysSyncUserBusiness.setUserId(userId);

        StringBuffer role = new StringBuffer();
        role.append(client + "_" + bean.getGroupNameId());
        role.append(":" + client + "_" + PingyinUtils.getPinYinHeadChar(bean.getGroupName()).toUpperCase() + ":" + bean.getGroupName());
        role.append(":0:" + bean.getBusinessName() + "@" + bean.getBusinessId());
        role.append(":客户," + client + ":::::");
        sysSyncUserBusiness.setRole(role.toString());
        iSysSyncUserBusinessService.insert(sysSyncUserBusiness);

        return sysSyncUserBusiness;
    }


    /**
     * 删除 SyncUserBusiness
     * @param businessId
     * @param userId
     * @param client
     */
    public void delSyncUserBusiness(Integer businessId, Integer userId, String client) {
        SysSyncUserBusiness delete = new SysSyncUserBusiness();
        delete.setBusinessId(businessId);
        delete.setUserId(userId);
        delete.setClientId(client);
        iSysSyncUserBusinessService.delete(new EntityWrapper<>(delete));
    }

    /**
     * 新增SyncUser
     * @param employeeDTO
     * @return
     */
    public int addSyncUser(EmployeeDTO employeeDTO) {


        SysSyncUser sysSyncUser = new SysSyncUser();
        sysSyncUser.setUsername(employeeDTO.getAccount());
        int i = iSysSyncUserService.selectCount(new EntityWrapper<>(sysSyncUser));
        if(i>0){
            throw new RuntimeException("登录账户被占用");
        }

        sysSyncUser.setPassword(employeeDTO.getPassword());
        sysSyncUser.setNickname(employeeDTO.getUserName());
        sysSyncUser.setPhone(employeeDTO.getMobile());

        String status = employeeDTO.getStatus();
        if("0".equals(status)){
            status="1";
        }else if("1".equals(status)){
            status="0";
        }
        sysSyncUser.setDelFlag(status);
        iSysSyncUserService.insert(sysSyncUser);
        Integer userId = sysSyncUser.getUserId();
        if (userId == null) {
            throw new RuntimeException("员工新增失败");
        }
        //更新角色
        changeUserRole(employeeDTO.getGroupNameId(),employeeDTO.getAccount());
        return userId;
    }

    /**
     * 编辑 SyncUser
     * @param employeeDTO
     */
    public SysSyncUser editSyncUser(EmployeeDTO employeeDTO) {

        SysSyncUser sysSyncUser = iSysSyncUserService.selectById(employeeDTO.getId());
        if (sysSyncUser == null) {
            throw new RuntimeException("员工id错误");
        }
        sysSyncUser.setUsername(employeeDTO.getAccount());
        if(StringUtils.isNotBlank(employeeDTO.getPassword())) {
            sysSyncUser.setPassword(employeeDTO.getPassword());
            //更新所有端密码
            SuccessTip tip = authFeign.updateUserPasswordAccount(employeeDTO.getAccount(), employeeDTO.getPassword(), "");
            if (tip.getCode() != 200) {
                throw new RuntimeException(tip.getMsg());
            }

            //更新老板账号到business表
            if(employeeDTO.getGroupNameId()==1){
                Business business = new Business();
                business.setId(employeeDTO.getBusinessId());
                business.setLoginPassword(employeeDTO.getPassword());
                business.setLoginUser(employeeDTO.getAccount());
                businessService.updateById(business);
            }

        }
        sysSyncUser.setNickname(employeeDTO.getUserName());
        sysSyncUser.setPhone(employeeDTO.getMobile());

        String status = employeeDTO.getStatus();
        if("0".equals(status)){
            status="1";
        }else if("1".equals(status)){
            status="0";
        }
        sysSyncUser.setDelFlag(status);

        iSysSyncUserService.updateById(sysSyncUser);

        // 更新  SyncUserBusiness

        SysSyncUserBusiness userId = new SysSyncUserBusiness();
        userId.setUserId(employeeDTO.getId());
        List<SysSyncUserBusiness> sysSyncUserBusinesses = iSysSyncUserBusinessService.selectList(new EntityWrapper<>(userId));
        Set<String> clientIds = sysSyncUserBusinesses.stream().map(SysSyncUserBusiness::getClientId).collect(Collectors.toSet());
        for (String clientId : clientIds) {
            delSyncUserBusiness(employeeDTO.getBusinessId(),employeeDTO.getId(),clientId);
            addSyncUserBusiness(employeeDTO,employeeDTO.getId(),clientId);
        }

        //更新角色
        changeUserRole(employeeDTO.getGroupNameId(),employeeDTO.getAccount());


        return sysSyncUser;
    }


    /**
     *  更改用户角色
     * @param roleId
     * @param account
     */
    public void changeUserRole(Integer roleId,String account){
        SellerGroup sellerGroup = iSellerGroupService.selectById(roleId);
        if(sellerGroup==null){
            throw new RuntimeException("角色id不存在");
        }
        //查找旧角色
        SellerGroupUser byAccount = new SellerGroupUser();
        byAccount.setLoginName(account);
        SellerGroupUser groupUser = iSellerGroupUserService.selectOne(new EntityWrapper<>(byAccount));
        if(groupUser!=null){
            //更新
            groupUser.setGroupCode(sellerGroup.getGroupCode());
            iSellerGroupUserService.update(groupUser,new EntityWrapper<>(byAccount));
        }else {
            //新增
            SellerGroupUser sellerGroupUser = new SellerGroupUser();
            sellerGroupUser.setGroupCode(sellerGroup.getGroupCode());
            sellerGroupUser.setLoginName(account);
            iSellerGroupUserService.insert(sellerGroupUser);
        }
    }

}
