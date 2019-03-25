package com.zhidianfan.pig.yd.moduler.manage.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.manage.dto.BusinessDTO;
import com.zhidianfan.pig.yd.moduler.manage.dto.PageDTO;
import com.zhidianfan.pig.yd.moduler.manage.dto.UserDTO;
import com.zhidianfan.pig.yd.moduler.manage.feign.AuthFeign;
import com.zhidianfan.pig.yd.moduler.manage.feign.SysDictFeign;
import com.zhidianfan.pig.yd.moduler.resv.dto.EmployeeDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipValueDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.BusinessEmployeeService;
import com.zhidianfan.pig.yd.moduler.resv.service.BusinessService;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.SmsFeign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/2
 * @Modified By:
 */
@Service
public class BusinessManageService {

    @Autowired
    private IBusinessService businessService;

    @Autowired
    private SysDictFeign sysDictFeign;

    @Autowired
    private AgentManageService agentManageService;

    @Autowired
    private AuthFeign authFeign;

    @Autowired
    private IAgentService agentService;

    @Autowired
    private ISnCodeService snCodeService;

    @Autowired
    SnCodeManageService snCodeManageService;

    @Autowired
    private IAndroidUserInfoService androidUserInfoService;

    @Autowired
    private ISellerUserService sellerUserService;

    @Autowired
    private ITableTypeService tableTypeService;

    @Autowired
    private ITableAreaService tableAreaService;

    @Autowired
    private IExternalSourceService externalSourceService;

    @Autowired
    private IVipValueService vipValueService;

    @Autowired
    private IBusinessSmsTemplateService businessSmsTemplateService;

    @Autowired
    private ISellerGroupService sellerGroupService;

    @Autowired
    private IBusinessUnorderReasonService businessUnorderReasonService;

    @Autowired
    private ISysSyncUserService sysSyncUserService;

    @Autowired
    private ISysSyncUserBusinessService sysSyncUserBusinessService;

    @Autowired
    private IBusinessSmsService businessSmsService;

    @Autowired
    private IBusinessSmsSettingService businessSmsSettingService;

    @Autowired
    BusinessEmployeeService resvBusinessService;

    @Autowired
    private ISmallAppUserService smallAppUserService;

    @Autowired
    private IAutoReceiptConfigService autoReceiptConfigService;

    @Autowired
    private IHotelStaffMappingService hotelStaffMappingService;

    @Autowired
    private ISellerGroupMenuService iSellerGroupMenuService;

    @Autowired
    private ISellerMenuService iSellerMenuService;

    @Autowired
    private SmsFeign smsFeign;


    /**
     * 获取酒店列表
     * @param businessDTO
     * @return
     */
    public PageDTO getBusiness(BusinessDTO businessDTO){
        Page<Business> businessPage = businessService.selectPage(new Page(businessDTO.getPage(),businessDTO.getRows()),new EntityWrapper<Business>().eq(null != businessDTO.getProvinceId(),"province_id",businessDTO.getProvinceId())
                .eq(null != businessDTO.getCityId(),"city_id",businessDTO.getCityId()).like(StringUtils.isNotBlank(businessDTO.getBusinessName()),"business_name",businessDTO.getBusinessName())
                .in(StringUtils.isNotBlank(businessDTO.getStatus()),"status",businessDTO.getStatus()).eq(StringUtils.isNotBlank(businessDTO.getBusinessAddress()),"business_address",businessDTO.getBusinessAddress())
                .eq(null != businessDTO.getAgentId(),"agent_id",businessDTO.getAgentId()).eq(null != businessDTO.getBusinessStar(),"business_star",businessDTO.getBusinessStar())
                .eq(null != businessDTO.getDevice(),"device",businessDTO.getDevice())
                .like(StringUtils.isNotBlank(businessDTO.getAgentName()),"agent_name",businessDTO.getAgentName())
                .orderBy(true,"status",true)
                .orderBy(true,"created_at",false));
        List<Map<String,Object>> list = new ArrayList<>();
        for(Business business : businessPage.getRecords()){
            Map<String,Object> businessMap = JsonUtils.object2Map(business);
            String agentProvince = "";
            String agentCity = "";
            String equal = "";
            if(null != business.getAgentId()){
                Agent agent = agentService.selectById(business.getAgentId());
                if(null != agent){
                    agentProvince = agent.getProvinceName();
                    agentCity = agent.getCityName();
                    equal = agent.getCityId().equals(business.getCityId())?"一致":"不一致";
                }
            }
            List<SnCode> snCodeList = snCodeService.selectList(new EntityWrapper<SnCode>().eq("business_id",business.getId()).eq("status",2));
            String snCodes = "";
            for(SnCode snCode : snCodeList){
                snCodes += snCode.getCode() + ",";
            }
            snCodes = snCodes.length() > 0 ? snCodes.substring(0,snCodes.length()-1):"";
            businessMap.put("agentProvince",agentProvince);
            businessMap.put("agentCity",agentCity);
            businessMap.put("equal",equal);
            businessMap.put("snCodes",snCodes);
            businessMap.put("statusName",sysDictFeign.getDict(String.valueOf(business.getStatus()),"business_status"));
            businessMap.put("id",String.valueOf(business.getId()));
            businessMap.put("appToken",business.getAppToken());
            list.add(businessMap);
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(businessPage.getCurrent());
        pageDTO.setTotal(businessPage.getPages());
        pageDTO.setRecords(businessPage.getTotal());
        pageDTO.setRows(list);
        return pageDTO;
    }


    /**
     * 未审核数量
     * @return
     */
    public Map<String, Integer> getBusinssCount(BusinessDTO businessDTO){
        Map<String,Integer> result = new HashMap<>();
        List<Business> businessList = businessService.selectList(new EntityWrapper<Business>().eq("status",'0').eq(null != businessDTO.getDevice(),"device",businessDTO.getDevice()));
        result.put("count",businessList.size());
        return result;
    }

    /**
     * 修改酒店信息
     * @param businessDTO
     * @return
     */
    @Transactional
    public Tip updateBusiness(BusinessDTO businessDTO){
        if(null == businessDTO.getId()){
            Tip tip = putApplyAgent(businessDTO);
            return tip;
        }else {
            Business business = new Business();
            BeanUtils.copyProperties(businessDTO,business);
            boolean isApply = false;
            business.setId(businessDTO.getId().intValue());
            business.setUpdatedAt(new Date());
            if("0".equals(String.valueOf(businessDTO.getOldStatus())) && !((String.valueOf(businessDTO.getOldStatus()).equals(businessDTO.getStatus())))){
                business.setReviewTime(new Date());
                isApply = true;
            }
            if(isApply && "1".equals(businessDTO.getStatus())){
                business.setOpenDate(new Date());
                Calendar curr = Calendar.getInstance();
                curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+1);
                Date date=curr.getTime();
                business.setDueDate(date);
                business.setTag("宝宝椅,不要辣,老板朋友,商务宴请");
                business.setTableTag("靠窗,安静,有电视");
            }
            boolean updateStatus = businessService.updateById(business);
            if(updateStatus){
                if(isApply && "1".equals(String.valueOf(businessDTO.getStatus()))){
                    try {
                        putBaseInfo(businessDTO);
                    }catch (Exception e){
                        e.printStackTrace();
                        throw new RuntimeException("账号已存在或添加失败");
                    }
                }else if(isApply && "2".equals(String.valueOf(businessDTO.getStatus()))){
                    SnCode snCode = new SnCode();
                    snCode.setBusinessId(Long.parseLong("0"));
                    snCode.setBusinessName("");
                    snCodeService.update(snCode,new EntityWrapper<SnCode>().eq("business_id",businessDTO.getId()));
                    agentManageService.putSuccessNotice(business.getOpenId(),new Date(),business.getBusinessName(),"酒店",false);
                    return new SuccessTip(200,"修改成功");
                }
                return new SuccessTip(200,"修改成功");
            } else {
                return new SuccessTip(4001,"修改失败");
            }
        }
    }

    /**
     * 酒店开户审核
     * @param businessDTO
     * @return
     */
    @Transactional
    public Tip putApplyAgent(BusinessDTO businessDTO) {
        SuccessTip successTip;
        Business business = new Business();
        BeanUtils.copyProperties(businessDTO,business);
        business.setCreatedAt(new Date());
        if("1".equals(businessDTO.getStatus())){
            business.setOpenDate(new Date());
            Calendar curr = Calendar.getInstance();
            curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+1);
            Date date=curr.getTime();
            business.setDueDate(date);
            business.setTag("宝宝椅,不要辣,老板朋友,商务宴请");
            business.setTableTag("靠窗,安静,有电视");
        }
        List<Business> businesses = businessService.selectList(new EntityWrapper<Business>().eq("login_user",business.getLoginUser()));
        if(businesses.size() > 0){
            successTip = new SuccessTip(4001,"用户名已存在");
        }else {
            boolean putStatus = businessService.insert(business);
            if(putStatus){
                if(null == businessDTO.getId()){
                    Business business1  = businessService.selectOne(new EntityWrapper<Business>().eq("login_user",business.getLoginUser()));
                    businessDTO.setId(business1.getId().longValue());
                }
                if("1".equals(businessDTO.getStatus())){
                    try {
                        putBaseInfo(businessDTO);
                    }catch (Exception e){
                        e.printStackTrace();
                        throw new RuntimeException("账号已存在或添加失败");
                    }
                }
                SysSyncUser sysSyncUser = new SysSyncUser();
                sysSyncUser.setNickname(businessDTO.getBussinessManager());
                sysSyncUserService.update(sysSyncUser,new EntityWrapper<SysSyncUser>().eq("phone",businessDTO.getPhone()));
                if(null != businessDTO.getSnCodes() && businessDTO.getSnCodes().length() > 0){
                    String[] snCodeList = businessDTO.getSnCodes().split(",");
                    for(String snCode : snCodeList){
                        SnCode snCode1 = new SnCode();
                        snCode1.setBusinessId(businessDTO.getId());
                        snCode1.setBusinessName(businessDTO.getBusinessName());
                        snCode1.setCode(snCode);
                        if("1".equals(businessDTO.getStatus())){
                            snCode1.setStatus(2);
                        }
                        snCode1.setUpdatedAt(new Date());
                        SnCode snCode2 = snCodeService.selectOne(new EntityWrapper<SnCode>().eq("code",snCode).eq("status",2).eq("business_id",0));
                        if(null == snCode2){
                            successTip = new SuccessTip(4002,snCode + "SN码不存在或已被绑定");
                            throw new RuntimeException("SN码不存在或已被绑定");
                        }
                        boolean updateStatus = snCodeService.update(snCode1,new EntityWrapper<SnCode>().eq("code",snCode));
                    }
                }
                String openid = sysDictFeign.getDict("business_openid","business_openid");
                agentManageService.putApplyNotice(openid,business.getCreatedAt(),business.getBusinessName(),"酒店");
                successTip = new SuccessTip(200,"添加成功");

                // 发送短信提醒公司 审核发送给胡鹏程
                String phone = "13028939980" ;
                String msg = String.format("叮叮，%s渠道提交%s 酒店开户申请，请及时处理。登录查看详情:   manager.zhidianfan.com"
                                            ,businessDTO.getAgentName(),businessDTO.getBusinessName());
                smsFeign.sendNormalMsg(phone,msg);

            }else {
                successTip = new SuccessTip(4001,"添加失败");
            }
        }
        return successTip;
    }

    /**
     * 开户添加基础信息
     * @param businessDTO
     * @return
     */
    public Tip putBaseInfo(BusinessDTO businessDTO){
        //添加USER模块账号
        putBaseUser(businessDTO);
        //添加安卓账号
        putBaseAndroidUser(businessDTO);
        //添加小程序用户账号
        putSmallAppUser(businessDTO);
        //添加新门店账号
        putBaseSellerUser(businessDTO);
        //添加默认角色
        putBaseSellerGroupUser(businessDTO);
        //开通短信
        putBaseSms(businessDTO);
        putBaseSmsSet(businessDTO);
        //添加桌型
        putBaseTableType(businessDTO);
        //添加区域
        putBaseTableArea(businessDTO);
        //添加外部来源
        putBaseSource(businessDTO);
        //添加客户价值
        putBaseVipValue(businessDTO);
        //添加短信模板
        putBaseSmsTemplate(businessDTO);
        //添加退订原因
        putBusinessReason(businessDTO);
        //添加员工表
        putBaseSysUser(businessDTO);
        //添加自动接单规则
        putAutoReceiptConfig(businessDTO);
        //添加角色默认菜单
        putRoleMenus();
        return new SuccessTip(200,"添加成功");
    }

    /**
     * 添加user模块账号
     * @param businessDTO
     */
    public void putBaseUser(BusinessDTO businessDTO){
        UserDTO userDTO = new UserDTO();
        userDTO.setClientId(sysDictFeign.getDict("WEB_MANAGER","user_type"));
        userDTO.setUsername(businessDTO.getLoginUser());
        userDTO.setPassword(businessDTO.getLoginPassword());
        userDTO.setPhone(businessDTO.getPhone());
        authFeign.createUserAdmin(userDTO);
        userDTO.setClientId(sysDictFeign.getDict("ANDROID_PHONE","user_type"));
        authFeign.createUserAdmin(userDTO);
        userDTO.setClientId(sysDictFeign.getDict("SMALL_APP","user_type"));
        authFeign.createUserAdmin(userDTO);
    }

    /**
     * 添加小程序用户账号
     * @param businessDTO
     */
    public void putSmallAppUser(BusinessDTO businessDTO){
        SmallAppUser smallAppUser = new SmallAppUser();
        smallAppUser.setLoginName(businessDTO.getLoginUser());
        smallAppUser.setPassword(businessDTO.getLoginPassword());
        smallAppUser.setTelphone(businessDTO.getPhone());
        smallAppUser.setBusinessId(String.valueOf(businessDTO.getId()));
        smallAppUserService.insert(smallAppUser);

        SmallAppUser smallAppUser1 = smallAppUserService.selectOne(new EntityWrapper<SmallAppUser>().eq("login_name",businessDTO.getLoginUser()).eq("business_id",String.valueOf(businessDTO.getId())));

        HotelStaffMapping hotelStaffMapping = new HotelStaffMapping();
        hotelStaffMapping.setHotelId(businessDTO.getId());
        hotelStaffMapping.setHotelName(businessDTO.getBusinessName());
        hotelStaffMapping.setStaffId(String.valueOf(smallAppUser1.getId()));
        hotelStaffMapping.setCreateTime(new Date());

        hotelStaffMappingService.insert(hotelStaffMapping);
    }

    /**
     * 添加安卓账号
     * @param businessDTO
     */
    public void putBaseAndroidUser(BusinessDTO businessDTO){
        AndroidUserInfo androidUserInfo = new AndroidUserInfo();
        androidUserInfo.setLoginName(businessDTO.getLoginUser());
        androidUserInfo.setUserName(businessDTO.getBussinessManager());
        androidUserInfo.setBusinessId(businessDTO.getId().intValue());
        androidUserInfo.setBusinessName(businessDTO.getBusinessName());
        androidUserInfo.setAppUserPassword(businessDTO.getLoginPassword());
        androidUserInfo.setStatus("1");
        androidUserInfoService.insert(androidUserInfo);
    }

    /**
     * 添加新门店账号
     * @param businessDTO
     */
    public void putBaseSellerUser(BusinessDTO businessDTO){
        SellerUser sellerUser = new SellerUser();
        sellerUser.setLoginName(businessDTO.getLoginUser());
        sellerUser.setPassword(businessDTO.getLoginPassword());
        sellerUser.setName(businessDTO.getBussinessManager());
        sellerUser.setTelphone(businessDTO.getPhone());
        sellerUser.setBusinessId(String.valueOf(businessDTO.getId()));
        sellerUser.setCreatedAt(new Date());
        sellerUserService.insert(sellerUser);
    }

    /**
     * 添加默认角色
     * @param businessDTO
     */
    public void putBaseSellerGroupUser(BusinessDTO businessDTO){
        SellerGroup sellerGroup = new SellerGroup();
        sellerGroup.setBusinessId(businessDTO.getId().intValue());

        sellerGroup.setGroupCode(businessDTO.getId() + "-1");
        sellerGroup.setGroupName("店长");
        sellerGroup.setDingdingUse((byte)1);
        sellerGroupService.insert(sellerGroup);
        sellerGroup.setGroupCode(businessDTO.getId() + "-2");
        sellerGroup.setGroupName("经理");
        sellerGroup.setDingdingUse((byte)1);
        sellerGroupService.insert(sellerGroup);
//        sellerGroup.setGroupCode(businessDTO.getId() + "-2");
//        sellerGroup.setGroupName("餐饮总监");
//        sellerGroupService.insert(sellerGroup);
//        sellerGroup.setGroupCode(businessDTO.getId() + "-3");
//        sellerGroup.setGroupName("餐饮部经理");
//        sellerGroupService.insert(sellerGroup);

//        sellerGroup.setGroupCode(businessDTO.getId() + "-5");
//        sellerGroup.setGroupName("前厅经理");
//        sellerGroupService.insert(sellerGroup);
//        sellerGroup.setGroupCode(businessDTO.getId() + "-6");
//        sellerGroup.setGroupName("预订经理");
//        sellerGroupService.insert(sellerGroup);
//        sellerGroup.setGroupCode(businessDTO.getId() + "-7");
//        sellerGroup.setGroupName("财务");
//        sellerGroupService.insert(sellerGroup);
        sellerGroup.setGroupCode(businessDTO.getId() + "-3");
        sellerGroup.setGroupName("收银");
        sellerGroup.setDingdingUse((byte)1);
        sellerGroupService.insert(sellerGroup);
        sellerGroup.setGroupCode(businessDTO.getId() + "-4");
        sellerGroup.setGroupName("前台");
        sellerGroup.setDingdingUse((byte)1);
        sellerGroupService.insert(sellerGroup);
    }

    /**
     * 添加桌型
     * @param businessDTO
     */
    public void putBaseTableType(BusinessDTO businessDTO){
        TableType tableType = new TableType();
        tableType.setBusinessId(businessDTO.getId().intValue());
        tableType.setMinPeopleNum(2);
        tableType.setMaxPeopleNum(4);
        tableType.setName("四人桌");
        tableTypeService.insert(tableType);
    }

    /**
     * 添加区域
     * @param businessDTO
     */
    public void putBaseTableArea(BusinessDTO businessDTO){
        TableArea tableArea = new TableArea();
        tableArea.setBusinessId(businessDTO.getId().intValue());
        tableArea.setCreatedAt(new Date());
        tableArea.setTableAreaName("一楼");
        tableAreaService.insert(tableArea);
    }

    /**
     * 添加外部来源
     * @param businessDTO
     */
    public void putBaseSource(BusinessDTO businessDTO){
        ExternalSource externalSource = new ExternalSource();
        externalSource.setBusinessId(businessDTO.getId().intValue());
        externalSource.setCreatedAt(new Date());
        externalSource.setExternalSourceName("电话预定");
        externalSourceService.insert(externalSource);
    }

    /**
     * 开通短信
     * @param businessDTO
     */
    public void putBaseSms(BusinessDTO businessDTO){
        BusinessSms businessSms = new BusinessSms();
        businessSms.setAdminName(businessDTO.getBussinessManager());
        businessSms.setAdminPhone(businessDTO.getBusinessPhone());
        businessSms.setBusinessId(businessDTO.getId().intValue());
        businessSms.setCreatedAt(new Date());
        businessSmsService.insert(businessSms);
    }

    /**
     * 开通短信1
     * @param businessDTO
     */
    public void putBaseSmsSet(BusinessDTO businessDTO){
        BusinessSmsSetting businessSmsSetting = new BusinessSmsSetting();
        businessSmsSetting.setBusinessId(businessDTO.getId().intValue());
        businessSmsSetting.setType(1);
        businessSmsSettingService.insert(businessSmsSetting);
        businessSmsSetting.setType(2);
        businessSmsSettingService.insert(businessSmsSetting);
        businessSmsSetting.setType(3);
        businessSmsSettingService.insert(businessSmsSetting);

    }

    /**
     * 添加客户价值
     * @param businessDTO
     */
    public void putBaseVipValue(BusinessDTO businessDTO){
        VipValueDTO vipValueDTO = new VipValueDTO();
        vipValueDTO.setActiveDays(30);
        vipValueDTO.setBusinessId(businessDTO.getId().intValue());
        vipValueDTO.setFlowDays(91);
        vipValueDTO.setHighValueAmount(10000);
        vipValueDTO.setHighValueCounts(3);
        vipValueDTO.setHighValueDays(30);
        vipValueDTO.setSleepDaysBegin(30);
        vipValueDTO.setSleepDaysEnd(90);
        vipValueDTO.setUnorderCount(3);
        vipValueService.insertVIPValueInfo(vipValueDTO);
    }

    /**
     * 添加短信模板
     * @param businessDTO
     */
    public void putBaseSmsTemplate(BusinessDTO businessDTO){
        businessSmsTemplateService.insertBusinessTemplate(businessDTO.getId().intValue());
    }

    /**
     * 添加基础员工
     * @param businessDTO
     */
    public void putBaseSysUser(BusinessDTO businessDTO){
        SysSyncUser sysSyncUser = new SysSyncUser();
        sysSyncUser.setCreateTime(new Date());
        sysSyncUser.setNickname(businessDTO.getBussinessManager());
        sysSyncUser.setUsername(businessDTO.getLoginUser());
        sysSyncUser.setPhone(businessDTO.getPhone());
        sysSyncUser.setPassword(businessDTO.getLoginPassword());
        boolean status = sysSyncUserService.insert(sysSyncUser);
        if(status){
            SysSyncUser sysSyncUser1 = sysSyncUserService.selectOne(new EntityWrapper<SysSyncUser>().eq("username",businessDTO.getLoginUser()));
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setGroupNameId(1);
            employeeDTO.setBusinessId(businessDTO.getId().intValue());
            employeeDTO.setBusinessName(businessDTO.getBusinessName());
            employeeDTO.setGroupName("老板");
            resvBusinessService.addSyncUserBusiness(employeeDTO,sysSyncUser1.getUserId(),"SELLER");
            resvBusinessService.addSyncUserBusiness(employeeDTO,sysSyncUser1.getUserId(),"ANDROID_PHONE");
            resvBusinessService.addSyncUserBusiness(employeeDTO,sysSyncUser1.getUserId(),"SMALL_APP");
        }
    }

    /**
     * 添加退订原因
     * @param businessDTO
     */
    public void putBusinessReason(BusinessDTO businessDTO){
        BusinessUnorderReason businessUnorderReason = new BusinessUnorderReason();
        businessUnorderReason.setBusinessId(String.valueOf(businessDTO.getId()));
        businessUnorderReason.setCreatedAt(new Date());
        businessUnorderReason.setSortId(0);
        businessUnorderReason.setUnorderReasonName("操作失误");
        businessUnorderReasonService.insert(businessUnorderReason);
        businessUnorderReason.setSortId(1);
        businessUnorderReason.setUnorderReasonName("客人要求");
        businessUnorderReasonService.insert(businessUnorderReason);
        businessUnorderReason.setSortId(2);
        businessUnorderReason.setUnorderReasonName("店内自身原因");
        businessUnorderReasonService.insert(businessUnorderReason);
    }

    /**
     * 添加自动接单规则
     * @param businessDTO
     */
    public void putAutoReceiptConfig(BusinessDTO businessDTO){
        AutoReceiptConfig autoReceiptConfig = new AutoReceiptConfig();
        autoReceiptConfig.setBusinessId(businessDTO.getId().intValue());
        autoReceiptConfig.setBusinessName(businessDTO.getBusinessName());
        autoReceiptConfig.setCreatedAt(new Date());
        autoReceiptConfigService.insert(autoReceiptConfig);
    }


    /**
     * 添加老板默认角色菜单
     */
    public void putRoleMenus(){

        //所有菜单
        SellerMenu byClient = new SellerMenu();
        byClient.setClientName("dd-manage");
        byClient.setIsenable("Y");
        List<SellerMenu> sellerMenus = iSellerMenuService.selectList(new EntityWrapper<>(byClient));

        //酒店下老板角色
        SellerGroup sellerGroups = sellerGroupService.selectById(1);
        List<SellerGroupMenu> list = new ArrayList<>();
            for (SellerMenu sellerMenu : sellerMenus) {
                SellerGroupMenu sellerGroupMenu = new SellerGroupMenu();
                sellerGroupMenu.setMenuCode(sellerMenu.getMenuCode());
                sellerGroupMenu.setGroupCode(sellerGroups.getGroupCode());
                list.add(sellerGroupMenu);
            }
        iSellerGroupMenuService.insertBatch(list);

    }

}
