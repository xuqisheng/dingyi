package com.zhidianfan.pig.user.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.user.dao.entity.Business;
import com.zhidianfan.pig.user.dao.entity.YdUser;
import com.zhidianfan.pig.user.dao.entity.YdUserRole;
import com.zhidianfan.pig.user.dao.service.IBusinessService;
import com.zhidianfan.pig.user.dao.service.IYdUserRoleService;
import com.zhidianfan.pig.user.dao.service.IYdUserService;
import com.zhidianfan.pig.user.dto.BusinessDTO;
import com.zhidianfan.pig.user.dto.PageDTO;
import com.zhidianfan.pig.user.dto.SuccessTip;
import com.zhidianfan.pig.user.dto.Tip;
import com.zhidianfan.pig.user.feign.SysDictFeign;
import com.zhidianfan.pig.user.dao.entity.*;
import com.zhidianfan.pig.user.dao.service.*;
import com.zhidianfan.pig.user.dto.*;
import com.zhidianfan.pig.user.utils.IgnorePropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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
    private SysDictFeign sysDictFeign;

    @Autowired
    private IYdUserService ydUserService;

    @Autowired
    private IYdUserRoleService ydUserRoleService;

    @Autowired
    private IMealTypeService mealTypeService;

    @Autowired
    private ITableAreaService tableAreaService;

    @Autowired
    private IBusinessThirdPartyService businessThirdPartyService;

    @Autowired
    private IBusinessUnorderReasonService businessUnorderReasonService;

    @Autowired
    private AgentService agentService;

    @Resource
    private ITableService iTableService;

    /**
     * 获取酒店列表
     * @param businessDTO
     * @return
     */
    public PageDTO getBusiness(BusinessDTO businessDTO){
        Page<Business> businessPage = businessService.selectPage(new Page(businessDTO.getPage(),businessDTO.getRows()),new EntityWrapper<Business>().eq(null != businessDTO.getProvinceId(),"province_id",businessDTO.getProvinceId())
                .eq(null != businessDTO.getCityId(),"city_id",businessDTO.getCityId()).like(StringUtils.isNotBlank(businessDTO.getBusinessName()),"business_name",businessDTO.getBusinessName())
                .eq(StringUtils.isNotBlank(businessDTO.getStatus()),"status",businessDTO.getStatus()).eq(StringUtils.isNotBlank(businessDTO.getBusinessAddress()),"business_address",businessDTO.getBusinessAddress())
                .eq(null != businessDTO.getAgentId(),"agent_id",businessDTO.getAgentId()).eq(null != businessDTO.getBusinessStar(),"business_star",businessDTO.getBusinessStar())
                .like(StringUtils.isNotBlank(businessDTO.getAgentName()),"agent_name",businessDTO.getAgentName())
                .orderBy(true,"created_at",false));
        List<Map<String,Object>> list = new ArrayList<>();
        for(Business business : businessPage.getRecords()){
            Map<String,Object> businessMap = JsonUtils.object2Map(business);
            businessMap.put("statusName",sysDictFeign.getDict(String.valueOf(business.getStatus()),"business_status"));
            businessMap.put("id",String.valueOf(business.getId()));
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
     * 修改酒店信息
     * @param businessDTO
     * @return
     */
    public Tip updateBusiness(BusinessDTO businessDTO){
        Business business = new Business();
        BeanUtils.copyProperties(businessDTO,business);
        boolean isApply = false;
        business.setUpdatedAt(new Date());
        if("0".equals(String.valueOf(businessDTO.getOldStatus())) && ((String.valueOf(businessDTO.getOldStatus()).equals(businessDTO.getStatus())))){
            business.setReviewTime(new Date());
            isApply = true;
        }
        boolean updateStatus = businessService.update(business,new EntityWrapper<Business>().eq("id",businessDTO.getId()));
        if(updateStatus){
            if(isApply && "1".equals(String.valueOf(businessDTO.getStatus()))){
                YdUser ydUser = new YdUser();
                ydUser.setClientId(sysDictFeign.getDict("business","user_type"));
                ydUser.setUsername(businessDTO.getUsername());
                ydUser.setPassword(businessDTO.getPassword());
                ydUser.setPhone(businessDTO.getBusinessPhone());
                ydUser.setCreateTime(new Date());
                boolean userStatus = ydUserService.insert(ydUser);
                YdUserRole ydUserRole = new YdUserRole();
                ydUserRole.setUsername(businessDTO.getUsername());
                ydUserRole.setRoleName("admin");
                boolean userRoleStatu = ydUserRoleService.insert(ydUserRole);
                if(userStatus && userRoleStatu){
                    agentService.putSuccessNotice(business.getOpenId(),new Date(),business.getBusinessName(),"酒店",true);
                    return new SuccessTip(200,"修改成功",null);
                }else {
                    return new SuccessTip(4001,"修改失败",null);
                }
            }else if(isApply && "2".equals(String.valueOf(businessDTO.getStatus()))){
                agentService.putSuccessNotice(business.getOpenId(),new Date(),business.getBusinessName(),"酒店",false);
                return new SuccessTip(200,"修改成功",null);
            }
            return new SuccessTip(200,"修改成功",null);
        } else {
            return new SuccessTip(4001,"修改失败",null);
        }
    }

    /**
     * 酒店开户审核
     * @param businessDTO
     * @return
     */
    public Tip putApplyAgent(BusinessDTO businessDTO){
        Business business = new Business();
        BeanUtils.copyProperties(businessDTO,business);
        business.setCreatedAt(new Date());
        List<Business> businesses = businessService.selectList(new EntityWrapper<Business>().eq("username",business.getUsername()));
        if(businesses.size() > 0){
            return new SuccessTip(4001,"用户名已存在",null);
        }else {
            boolean putStatus = businessService.insert(business);
            if(putStatus){
                String openid = sysDictFeign.getDict("business_openid","business_openid");
                agentService.putApplyNotice(openid,business.getCreatedAt(),business.getBusinessName(),"酒店");
                return new SuccessTip(200,"添加成功",null);
            }else {
                return new SuccessTip(4001,"添加失败",null);
            }
        }
    }


    /**
     * 酒店信息修改
     * @param businessDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
   public Tip editInfo(BusinessDTO businessDTO){
       Business condition = new Business();
       Business newBusiness = new Business();
       BeanUtils.copyProperties(businessDTO,newBusiness, IgnorePropertiesUtil.getNullPropertyNames(businessDTO));
       boolean update = businessService.update(newBusiness, new EntityWrapper<>(condition));

       //删除标签把对应桌位里的也删除
       deleteTableTag(businessDTO);

       return update?SuccessTip.SUCCESS_TIP: ErrorTip.ERROR_TIP;
   }

    /**
     * 删除桌位里的指定标签
     * @param businessDTO
     */
    private void deleteTableTag(BusinessDTO businessDTO) {
        Long id = businessDTO.getId();
        List<String> deleteTableTag = businessDTO.getDeleteTableTag();
        if(deleteTableTag!=null){
            for (String tag : deleteTableTag) {
                List<Table> tables = iTableService.findByTableTag(tag, id);
                for (Table table : tables) {
                    String[] split = table.getRelatedTable().split(",");
                    List<String> tags = Arrays.asList(split);
                    tags=new ArrayList<>(tags);
                    for (String s : tags) {
                        if(tag.equals(s)){
                            tags.remove(s);
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
                .eq("business_id", id));
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
    public boolean changeDefMealsNum(Long id, Integer defMealsNum) {

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
    public boolean changeOrderExemptionVerifyCheck(Long id, Integer orderExemptionVerifyCheck) {

        Business business = new Business();
        business.setId(id);
        business.setOrderExemptionVerifyCheck(orderExemptionVerifyCheck);

        return  businessService.update(business, new EntityWrapper<Business>()
                .eq("id", id));
    }


    /**
     * 酒店关联的第三方平台信息刪除
     */
    public  void  deleteThirdPartyById(Integer id){

        businessThirdPartyService.deleteById(id);
    }


    /**
     * 酒店关联的第三方平台信息新增
     * @param businessThirdParty
     */
    public void insertThirdParty(BusinessThirdParty businessThirdParty) {

        BusinessThirdParty condition = new BusinessThirdParty();
        condition.setBusinessId(businessThirdParty.getBusinessId());

        EntityWrapper<BusinessThirdParty> wrapper = new EntityWrapper<>(condition);
        wrapper.orderBy("sort",false);
        BusinessThirdParty one = businessThirdPartyService.selectOne(wrapper);
        if(one==null){
            businessThirdParty.setSort(0);
        }else {
            businessThirdParty.setSort(one.getSort()+1);
        }
        businessThirdPartyService.insert(businessThirdParty);
    }

    /**
     * 获取酒店启用的退订原因
     * @param businessId
     * @return
     */
    public List<BusinessUnorderReason> getReasonList(Integer businessId) {

        List<BusinessUnorderReason> business_id = businessUnorderReasonService.selectList(new EntityWrapper<BusinessUnorderReason>()
                .eq("status",1)
                .eq("business_id", businessId));

        return business_id;
    }


    /**
     * 修改订单来源
     * @param businessThirdPartyDTO
     * @return
     */
    public boolean editThirdParty(BusinessThirdPartyDTO businessThirdPartyDTO){

        //排序
        List<SortIds> idAndSortIds = businessThirdPartyDTO.getIdAndSortIds();
        if(idAndSortIds!=null) {
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

}
