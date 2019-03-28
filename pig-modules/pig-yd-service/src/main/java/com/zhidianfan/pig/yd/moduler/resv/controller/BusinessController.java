package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.common.util.PasswordUtils;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessUnorderReasonService;
import com.zhidianfan.pig.yd.moduler.common.service.ISellerGroupService;
import com.zhidianfan.pig.yd.moduler.resv.bo.BusinessBrandBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.EmpChangeBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.EmployeeBo;
import com.zhidianfan.pig.yd.moduler.resv.dto.BusinessDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.BusinessThirdPartyDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.BusinessUnorderReasonDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.EmployeeDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.BusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/2
 * @Modified By:
 */
@Api("酒店")
@RestController
@RequestMapping("/business")
public class
BusinessController {

    @Autowired
    private BusinessService businessService;

    @Resource
    private IBusinessService iBusinessService;

    @Resource
    private IBusinessUnorderReasonService ibusinessUnorderReasonService;

    @Resource
    private ISellerGroupService iSellerGroupService;




    /**
     * 获取酒店信息
     *
     * @param id 酒店id
     * @return 返回酒店信息
     */
    @ApiOperation("获取酒店信息")
    @GetMapping("/getbusinessinfo")
    public Business getBusinessInfo(@RequestParam("id") Integer id) {
        Business businessinfo = businessService.getBusinessInfo(id);
        return businessinfo;
    }


    /**
     * 获取餐别
     *
     * @param id 酒店id
     * @return 返回餐别
     */
    @GetMapping("/getmealtypeinfo")
    public List<MealType> getMealTypeInfo(@RequestParam("id")Integer id) {
        List<MealType> mealtype = businessService.getMealTypeInfo(id);
        return mealtype;
    }

    /**
     * 获取酒店区域信息
     *
     * @param id 酒店id
     * @return 返回酒店区域信息
     */
    @GetMapping("/gettableareainfo")
    public List<TableArea> getTableAreaInfo(@RequestParam("id")Integer id) {
        List<TableArea> areainfo = businessService.getTableAreaInfo(id);

        return areainfo;
    }


    /**
     * 有效的客户合约平台
     *
     * @param id 酒店id
     * @return
     */
    @ApiOperation("来源列表")
    @GetMapping("/getthirdpartyinfo")
    public List<BusinessThirdParty> getThirdPartyInfo(@RequestParam("id")Integer id) {

        List<BusinessThirdParty> businessThirdPartyList = businessService.getThirdPartyInfo(id);
        return businessThirdPartyList;
    }

    /**
     * 修改默认就餐人数
     * @param business  酒店id  默认就餐人数
     * @return
     */
    @PostMapping("/defmealsnum")
    public boolean changeDefMealsNum(@RequestBody Business business){
        return businessService.changeDefMealsNum(business.getId(),business.getDefMealsNum());
    }


    /**
     * 修改酒店免审订单按钮状态
     *
     * @param business      酒店id  orderExemptionVerifyCheck 按钮状态
     * @return true代表操作成功
     */
    @PostMapping("/exemptionVerifyCheck")
    public boolean changeOrderExemptionVerifyCheck(@RequestBody Business business) {

        return businessService.changeOrderExemptionVerifyCheck(business.getId(),business.getOrderExemptionVerifyCheck());

    }

    /**
     * 酒店关联的第三方平台信息删除
     * @param id 酒店id
     */
    @ApiOperation("删除来源")
    @GetMapping("/thirdparty")
    public  void  deleteThirdPartyById(@RequestParam("id") Integer id){
        businessService.deleteThirdPartyById(id);
    }

    /**
     * 酒店关联的第三方平台信息新增
     * @param businessThirdParty
     * @return
     */
    @ApiOperation("新增来源")
    @PostMapping("/thirdparty")
    public  void insertThirdParty(@RequestBody BusinessThirdParty businessThirdParty){
        businessService.insertThirdParty(businessThirdParty);
    }


    @ApiOperation("修改来源")
    @PostMapping("/editThirdparty")
    public  ResponseEntity editThirdparty(@RequestBody BusinessThirdPartyDTO businessThirdParty){
        boolean b = businessService.editThirdParty(businessThirdParty);
        return ResponseEntity.ok(b? SuccessTip.SUCCESS_TIP: ErrorTip.ERROR_TIP);
    }


    /**
     * 获取酒店启用的退订原因
     * @param businessId 酒店id
     * @return
     */
    @ApiOperation("退订原因")
    @GetMapping("/unorderreason")
    public List<BusinessUnorderReason> getReasonList(@RequestParam("businessId")Integer businessId) {

        List<BusinessUnorderReason> reasonList = businessService.getReasonList(businessId);
        return reasonList;
    }

    /**
     * 获取酒店启用的退订原因
     * @param
     * @return
     */
    @ApiOperation("添加退订原因")
    @PostMapping("/unOrderReason/add")
    public ResponseEntity<Tip> addReason(@Valid BusinessUnorderReasonDTO businessUnorderReasonDTO) {

        BusinessUnorderReason businessUnorderReason = new BusinessUnorderReason();
        BeanUtils.copyProperties(businessUnorderReasonDTO,businessUnorderReason);
        boolean insert = ibusinessUnorderReasonService.insert(businessUnorderReason);
        return ResponseEntity.ok(insert?SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP);
    }

    @ApiOperation("删除退订原因")
    @GetMapping("/unOrderReason/delete")
    public ResponseEntity<Tip> delete(Integer id) {

        boolean b = ibusinessUnorderReasonService.deleteById(id);
        return ResponseEntity.ok(b?SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP);
    }


    @ApiOperation("基础信息修改")
    @PostMapping("/editInfo")
    public ResponseEntity<Tip> editInfo(@Valid BusinessDTO businessDTO){
        Tip tip = businessService.editInfo(businessDTO);
        return ResponseEntity.ok(tip);
    }






    /**
     * 酒店备注标签
     * @param businessId
     * @return
     */
    @ApiOperation("酒店备注标签")
    @GetMapping(value = "/tag/all")
    public ResponseEntity all(@RequestParam("businessId") Integer businessId) {
        Business business = getBusiness(businessId);
        Map<String, String> tag = new HashMap<>();
        tag.put("tag",business.getTag());
        return ResponseEntity.ok(tag);
    }


    /**
     * 酒店备注标签新增 修改 删除
     * @param tag
     * @return
     */
    @ApiOperation("酒店备注修改")
    @PostMapping(value = "/tag/edit")
    public ResponseEntity edit(Integer businessId,String tag) {
        Business business = getBusiness(businessId);
        if(StringUtils.isBlank(tag)){
            tag="";
        }
        business.setTag(tag);
        ResponseEntity responseEntity = updateTag(business);
        return responseEntity;

    }



    private Business getBusiness(Integer businessId) {
        Business business = iBusinessService.selectById(businessId);
        if(business==null){
            throw new RuntimeException("酒店id不存在");
        }
        return business;
    }
    private ResponseEntity updateTag(Business business) {
        boolean result = iBusinessService.updateAllColumnById(business);
        return ResponseEntity.ok(result);
    }


    @ApiOperation("员工列表")
    @GetMapping(value = "/employee/list")
    public ResponseEntity<List<EmployeeBo>> employeeList(Integer businessId,String name,String mobile,String status) {
        List<EmployeeBo> employeeBos = businessService.employeeList(businessId, name, mobile, status);
        return ResponseEntity.ok(employeeBos);

    }


    @ApiOperation("员工编辑")
    @PostMapping(value = "/employee/edit")
    public ResponseEntity employeeEdit(@RequestBody EmployeeDTO employeeDTO) {
        if(StringUtils.isNotEmpty(employeeDTO.getPassword()) && !PasswordUtils.checkSimplePassword(employeeDTO.getPassword())){
            return ResponseEntity.badRequest().body(ErrorTip.ERROR_TIP);
        }
        businessService.employeeEdit(employeeDTO);
        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }


    @ApiOperation("用户角色")
    @GetMapping(value = "/employee/role",params = {"businessId"})
    public ResponseEntity<List<SellerGroup>> employeeList(Integer businessId) {
        SellerGroup sellerUser = new SellerGroup();
        sellerUser.setBusinessId(businessId);
        sellerUser.setDingdingUse((byte)1);
        List<SellerGroup> appTypes = iSellerGroupService.selectList(new EntityWrapper<>(sellerUser).or("group_code = 'sadmin'"));
        return ResponseEntity.ok(appTypes);

    }

    @ApiOperation("集团下酒店列表")
    @GetMapping(value = "/brand/list",params = {"businessId"})
    public ResponseEntity getBusinessBrandList(Integer businessId){
        List<BusinessBrandBo> list = businessService.getBusinessBrandList(businessId);
        return ResponseEntity.ok(list);
    }

    @ApiOperation("小程序可切换酒店列表")
    @GetMapping(value = "/change/list",params = {"userId"})
    public ResponseEntity getEmpBrandList(Integer userId){
        List<BusinessBrandBo> list = businessService.getEmpBrandList(userId,1);
        return ResponseEntity.ok(list);
    }

    @ApiOperation("小程序切换酒店")
    @PostMapping(value = "/small/change")
    public ResponseEntity changeSmallBusiness(@Valid EmpChangeBo empChangeBo){
        boolean b = businessService.changeEmpBusiness(empChangeBo);
        if(b){
            return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
        }else {
            return ResponseEntity.ok(ErrorTip.ERROR_TIP);
        }
    }

    @ApiOperation("门店后台切换酒店")
    @PostMapping(value = "/seller/change")
    public ResponseEntity changeSellerBusiness(@Valid EmpChangeBo empChangeBo){
        boolean b = businessService.changeSellerBusiness(empChangeBo);
        if(b){
            return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
        }else {
            return ResponseEntity.ok(ErrorTip.ERROR_TIP);
        }
    }
}
