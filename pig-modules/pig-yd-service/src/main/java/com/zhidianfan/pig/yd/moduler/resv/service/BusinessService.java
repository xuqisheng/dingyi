package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.SuccessTip;

import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.resv.bo.BusinessBrandBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.EmpChangeBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.EmployeeBo;
import com.zhidianfan.pig.yd.moduler.resv.dto.BusinessDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.BusinessThirdPartyDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.EmployeeDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.SortIds;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/2
 * @Modified By:
 */
@Service
public class BusinessService {

    @Autowired
    private IBusinessService businessService;


    @Autowired
    private IMealTypeService mealTypeService;

    @Autowired
    private ITableAreaService tableAreaService;

    @Autowired
    private IBusinessThirdPartyService businessThirdPartyService;

    @Autowired
    private IBusinessUnorderReasonService businessUnorderReasonService;

    @Resource
    private ITableService iTableService;



    @Resource
    private ISysSyncUserService iSysSyncUserService;

    @Resource
    private ISysSyncUserBusinessService iSysSyncUserBusinessService;



    @Resource
    private BusinessEmployeeService businessEmployeeService;

    @Autowired
    private IBrandService brandService;

    @Autowired
    private ISmallAppUserService iSmallAppUserService;

    @Autowired
    private IHotelStaffMappingService hotelStaffMappingService;

    @Autowired
    private ISellerUserService sellerUserService;



    /**
     * 酒店信息修改
     *
     * @param businessDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Tip editInfo(BusinessDTO businessDTO) {

        Business newBusiness = new Business();
        BeanUtils.copyProperties(businessDTO, newBusiness);
        boolean update = businessService.updateById(newBusiness);

        //删除标签把对应桌位里的也删除
        deleteTableTag(businessDTO);

        return update ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP;
    }

    /**
     * 删除桌位里的指定标签
     *
     * @param businessDTO
     */
    private void deleteTableTag(BusinessDTO businessDTO) {
        Integer id = businessDTO.getId();
        List<String> deleteTableTag = businessDTO.getDeleteTableTag();
        if (deleteTableTag != null) {
            for (String tag : deleteTableTag) {
                List<Table> tables = iTableService.findByTableTag(tag, id);
                for (Table table : tables) {
                    String[] split = table.getRelatedTable().split(",");
                    List<String> tags = new ArrayList<>(Arrays.asList(split));
                    if(tags==null){
                        continue;
                    }
                    Iterator<String> iterator = tags.iterator();
                    while (iterator.hasNext()){
                        String s = iterator.next();
                        if (tag.equals(s)) {
                            iterator.remove();
                        }
                    }

                    String newRelatedTable = String.join(",", tags);
                    table.setRelatedTable(newRelatedTable);
                    iTableService.updateById(table);
                }
            }
        }
    }


    /**
     * 获取酒店信息
     *
     * @param id 酒店id
     * @return 返回酒店信息
     */
    public Business getBusinessInfo(Integer id) {
        Business businessinfo = businessService.selectOne(new EntityWrapper<Business>()
                .eq("id", id).eq("status", 1));
        return businessinfo;
    }


    /**
     * 获取餐别
     *
     * @param id 酒店id
     * @return 返回餐别
     */
    public List<MealType> getMealTypeInfo(Integer id) {
        List<MealType> mealtype = mealTypeService.selectList(new EntityWrapper<MealType>()
                .eq("business_id", id)
                .eq("status", "1")
                .eq("isnew", 1));
        return mealtype;
    }


    /**
     * 获取酒店区域信息
     *
     * @param id 酒店id
     * @return 返回酒店区域信息
     */
    public List<TableArea> getTableAreaInfo(Integer id) {
        List<TableArea> areainfo = tableAreaService.selectList(new EntityWrapper<TableArea>()
                .eq("business_id", id));
        return areainfo;
    }


    /**
     * 有效的客户合约平台
     *
     * @param id
     * @return
     */
    public List<BusinessThirdParty> getThirdPartyInfo(Integer id) {

        List<BusinessThirdParty> businessThirdPartyList = businessThirdPartyService.findThirdPartyByBusinessId(id);
        return businessThirdPartyList;
    }


    /**
     * 修改酒店默认就餐人数
     *
     * @param id          酒店id
     * @param defMealsNum 默认就餐人数
     * @return true代表操作成功
     */
    public boolean changeDefMealsNum(Integer id, Integer defMealsNum) {

        Business business = new Business();
        business.setId(id);
        business.setDefMealsNum(defMealsNum);

        return businessService.update(business, new EntityWrapper<Business>()
                .eq("id", id));

    }


    /**
     * 修改酒店免审订单按钮状态
     *
     * @param id                        酒店id
     * @param orderExemptionVerifyCheck 按钮状态
     * @return true代表操作成功
     */
    public boolean changeOrderExemptionVerifyCheck(Integer id, Integer orderExemptionVerifyCheck) {

        Business business = new Business();
        business.setId(id);
        business.setOrderExemptionVerifyCheck(orderExemptionVerifyCheck);

        return businessService.update(business, new EntityWrapper<Business>()
                .eq("id", id));
    }


    /**
     * 酒店关联的第三方平台信息刪除
     */
    public void deleteThirdPartyById(Integer id) {

        businessThirdPartyService.deleteById(id);
    }


    /**
     * 酒店关联的第三方平台信息新增
     *
     * @param businessThirdParty
     */
    public void insertThirdParty(BusinessThirdParty businessThirdParty) {

        BusinessThirdParty condition = new BusinessThirdParty();
        condition.setBusinessId(businessThirdParty.getBusinessId());

        EntityWrapper<BusinessThirdParty> wrapper = new EntityWrapper<>(condition);
        wrapper.orderBy("sort", false);
        BusinessThirdParty one = businessThirdPartyService.selectOne(wrapper);
        if (one == null) {
            businessThirdParty.setSort(0);
        } else {
            businessThirdParty.setSort(one.getSort() + 1);
        }
        businessThirdPartyService.insert(businessThirdParty);
    }

    /**
     * 获取酒店启用的退订原因
     *
     * @param businessId
     * @return
     */
    public List<BusinessUnorderReason> getReasonList(Integer businessId) {

        List<BusinessUnorderReason> business_id = businessUnorderReasonService.selectList(new EntityWrapper<BusinessUnorderReason>()
                .eq("status", 1)
                .eq("business_id", businessId));

        return business_id;
    }


    /**
     * 修改订单来源
     *
     * @param businessThirdPartyDTO
     * @return
     */
    public boolean editThirdParty(BusinessThirdPartyDTO businessThirdPartyDTO) {

        //排序
        List<SortIds> idAndSortIds = businessThirdPartyDTO.getIdAndSortIds();
        if (idAndSortIds != null) {
            ArrayList<BusinessThirdParty> list = new ArrayList<>();
            for (SortIds idAndSortId : idAndSortIds) {
                BusinessThirdParty area = new BusinessThirdParty();
                area.setId(idAndSortId.getId());
                area.setSort(idAndSortId.getSortIds());
                list.add(area);
            }
            boolean b = businessThirdPartyService.updateBatchById(list);
            return b;
        }
        //预留修改其他属性

        return false;
    }


    /**
     * 员工列表
     *
     * @param businessId
     * @param name
     * @param mobile
     * @param status
     * @return
     */
    public List<EmployeeBo> employeeList(Integer businessId, String name, String mobile, String status) {

        List<EmployeeBo> result = new ArrayList<>();

        SysSyncUserBusiness condition = new SysSyncUserBusiness();
        condition.setBusinessId(businessId);


        List<SysSyncUserBusiness> sysSyncUserBusinesses = iSysSyncUserBusinessService.selectList(new EntityWrapper<>(condition));
        //所有员工id
        Set<Integer> userIds = sysSyncUserBusinesses.stream().map(SysSyncUserBusiness::getUserId).collect(Collectors.toSet());

        if(userIds==null){
            return result;
        }

        List<SysSyncUser> sysSyncUsers = iSysSyncUserService.selectBatchIds(userIds);

        //条件筛选
        Iterator<SysSyncUser> iterator = sysSyncUsers.iterator();

        while (iterator.hasNext()) {
            SysSyncUser sysSyncUser = iterator.next();
            if (
                        status != null && !sysSyncUser.getDelFlag().equals(status)
                    ||StringUtils.isNotBlank(name) && !StringUtils.contains(sysSyncUser.getNickname() ,name)
                    ||StringUtils.isNotBlank(mobile) && !StringUtils.contains(sysSyncUser.getPhone(),mobile)
            ) {
                iterator.remove();
            }

            //给update默认时间方便排序
            if(sysSyncUser.getUpdateTime()==null){
                sysSyncUser.setUpdateTime(sysSyncUser.getCreateTime());
            }
        }

        //排序（按创建时间正序）
        sysSyncUsers.sort(comparing(SysSyncUser::getCreateTime));

        List<String> devices = Arrays.asList("SELLER", "ANDROID_PHONE","SMALL_APP");
            for (SysSyncUser sysSyncUser : sysSyncUsers) {

            String userRole = null;
            EmployeeBo employeeBo = new EmployeeBo();
            employeeBo.setMobile(sysSyncUser.getPhone());
            employeeBo.setAccount(sysSyncUser.getUsername());
            employeeBo.setName(sysSyncUser.getNickname());
            employeeBo.setDelFlag(sysSyncUser.getDelFlag());
            employeeBo.setId(sysSyncUser.getUserId());

            //门店后台
            Optional<SysSyncUserBusiness> seller = sysSyncUserBusinesses.stream().filter(
                    item ->
                            item.getBusinessId().equals(businessId)
                                    && "SELLER".equals(item.getClientId())
                                    && sysSyncUser.getUserId().equals(item.getUserId())
            ).findFirst();
            if (seller.isPresent()) {
                SysSyncUserBusiness sysSyncUserBusiness = seller.get();
                employeeBo.setLoginDevices(new ArrayList<>(Arrays.asList("门店后台")));
                userRole = sysSyncUserBusiness.getRole().split(":")[2];

                //获取门店后台用户id以及可登陆酒店列表
                SellerUser sellerUser = sellerUserService.selectOne(new EntityWrapper<SellerUser>().eq("login_name",sysSyncUser.getUsername()));
                if(sellerUser != null){
                    employeeBo.setSellerUserId(sellerUser.getId());
                    List<BusinessBrandBo> list = getEmpBrandList(sellerUser.getId(),2);
                    employeeBo.setSellerBusinessList(list);
                }
            }
            //安卓电话机
            Optional<SysSyncUserBusiness> androidPhone = sysSyncUserBusinesses.stream().filter(
                    item ->
                            item.getBusinessId().equals(businessId)
                                    && "ANDROID_PHONE".equals(item.getClientId())
                                    && sysSyncUser.getUserId().equals(item.getUserId())
            ).findFirst();
            if (androidPhone.isPresent()) {
                SysSyncUserBusiness sysSyncUserBusiness = androidPhone.get();
                List<String> loginDevices = employeeBo.getLoginDevices();
                if(loginDevices==null){
                    loginDevices=new ArrayList<>();
                }
                loginDevices.add("电话机");
                employeeBo.setLoginDevices(loginDevices);
                userRole = sysSyncUserBusiness.getRole().split(":")[2];
            }

            //小程序
                Optional<SysSyncUserBusiness> smallApp = sysSyncUserBusinesses.stream().filter(
                        item ->
                                item.getBusinessId().equals(businessId)
                                        && "SMALL_APP".equals(item.getClientId())
                                        && sysSyncUser.getUserId().equals(item.getUserId())
                ).findFirst();
                if (smallApp.isPresent()) {
                    SysSyncUserBusiness sysSyncUserBusiness = smallApp.get();
                    List<String> loginDevices = employeeBo.getLoginDevices();
                    if(loginDevices==null){
                        loginDevices=new ArrayList<>();
                    }
                    loginDevices.add("小程序");
                    employeeBo.setLoginDevices(loginDevices);
                    userRole = sysSyncUserBusiness.getRole().split(":")[2];

                    //获取小程序用户id以及可登陆酒店列表
                    SmallAppUser smallAppUser = iSmallAppUserService.selectOne(new EntityWrapper<SmallAppUser>().eq("login_name",sysSyncUser.getUsername()));
                    if(smallAppUser != null){
                        employeeBo.setUserId(smallAppUser.getId());
                        List<BusinessBrandBo> businessList = getEmpBrandList(smallAppUser.getId(),1);
                        employeeBo.setBusinessList(businessList);
                    }
                }

            employeeBo.setUserRole(userRole);
            result.add(employeeBo);

        }


        return result;
    }


    @Transactional(rollbackFor = Exception.class)
    public void employeeEdit(EmployeeDTO bean) {
        String option = bean.getOption();
        if ("ADD".equals(option)) {
            businessEmployeeService.addEmployee(bean);
        } else if ("EDIT".equals(option)) {
            businessEmployeeService.editEmployee(bean);
        }
    }


    /**
     * 获取集团下的酒店
     * @param businessId
     * @return
     */
    public List<BusinessBrandBo> getBusinessBrandList(Integer businessId){

        //获取集团id
        Business business = businessService.selectById(businessId);
        Integer brandId = business.getBrandId();

        //查找集团下的酒店
        List<Business> businessList = businessService.selectList(new EntityWrapper<Business>().eq("brand_id",brandId).eq("device",2).eq("status",'1'));

        List<BusinessBrandBo> list = new ArrayList();
        for(Business business1 : businessList){
            BusinessBrandBo businessBrandBo = new BusinessBrandBo();
            businessBrandBo.setBusinessId(business1.getId());
            businessBrandBo.setBusinessName(business1.getBusinessName());
            list.add(businessBrandBo);
        }

        return list;
    }

    /**
     * 可切换酒店列表
     * @param userId
     * @return
     */
    public List<BusinessBrandBo> getEmpBrandList(Integer userId,Integer type){

        List<HotelStaffMapping> hotelStaffMappingList = hotelStaffMappingService.selectList(new EntityWrapper<HotelStaffMapping>().eq("staff_id",String.valueOf(userId)).eq("status",true).eq("type",type));

        List<BusinessBrandBo> list = new ArrayList();
        for(HotelStaffMapping hotelStaffMapping : hotelStaffMappingList){
            BusinessBrandBo businessBrandBo = new BusinessBrandBo();
            businessBrandBo.setBusinessId(Integer.valueOf(String.valueOf(hotelStaffMapping.getHotelId())));
            businessBrandBo.setBusinessName(hotelStaffMapping.getHotelName());
            list.add(businessBrandBo);
        }

        return list;
    }


    /**
     * 小程序员工切换酒店
     * @param empChangeBo
     * @return
     */
    public boolean changeEmpBusiness(EmpChangeBo empChangeBo){

        SmallAppUser smallAppUser = new SmallAppUser();

        smallAppUser.setBusinessId(String.valueOf(empChangeBo.getNewBusinessId()));
        smallAppUser.setUpdatedAt(new Date());

        return iSmallAppUserService.update(smallAppUser,new EntityWrapper<SmallAppUser>().eq("business_id",empChangeBo.getOldBusinessId()).eq("id",empChangeBo.getUserId()));

    }

    /**
     * 门店后台切换酒店
     * @param empChangeBo
     * @return
     */
    public boolean changeSellerBusiness(EmpChangeBo empChangeBo){

        SellerUser sellerUser = new SellerUser();

        sellerUser.setBusinessId(String.valueOf(empChangeBo.getNewBusinessId()));
        sellerUser.setUpdatedAt(new Date());

        return sellerUserService.update(sellerUser,new EntityWrapper<SellerUser>().eq("business_id",empChangeBo.getOldBusinessId()).eq("id",empChangeBo.getUserId()));

    }

}
