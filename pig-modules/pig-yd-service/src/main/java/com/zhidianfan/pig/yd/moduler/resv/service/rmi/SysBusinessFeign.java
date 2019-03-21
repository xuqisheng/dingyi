package com.zhidianfan.pig.yd.moduler.resv.service.rmi;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.fallback.SysBusinessFeignFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/28
 * @Modified By:
 */
@FeignClient(name = "pig-yd-user", fallbackFactory = SysBusinessFeignFactory.class)
public interface SysBusinessFeign {


    /**
     * 获取酒店信息
     *
     * @param id 酒店id
     * @return 返回酒店信息
     */
    @GetMapping("/business/getbusinessinfo")
    Business getBusinessInfo(@RequestParam("id") Integer id);


    /**
     * 获取餐别
     *
     * @param id 酒店id
     * @return 返回餐别
     */
    @GetMapping("/business/getmealtypeinfo")
    List<MealType> getMealTypeInfo(@RequestParam("id")Integer id);

    /**
     * 获取酒店区域信息
     *
     * @param id 酒店id
     * @return 返回酒店区域信息
     */
    @GetMapping("/business/gettableareainfo")
    List<TableArea> getTableAreaInfo(@RequestParam("id")Integer id);

    @GetMapping("/business/getthirdpartyinfo")
    List<BusinessThirdParty> getThirdPartyInfo(@RequestParam("id")Integer id);

    /**
     * 修改酒店默认就餐人数
     *
     * @param business    酒店id  defMealsNum 默认就餐人数
     * @return true代表操作成功
     */
    @PostMapping("/business/defmealsnum")
    boolean changeDefMealsNum(@RequestBody Business business);


    /**
     * 修改第三方订单免审状态
     * @param business 酒店id 与按钮状态
     * @return 成功操作与否
     */
    @PostMapping("/business/exemptionVerifyCheck")
    boolean changeOrderExemptionVerifyCheck(@RequestBody Business business);


    /**
     * 酒店关联的第三方平台信息删除
     * @param id 平台id
     * @return
     */
    @DeleteMapping("/business/thirdparty")
    void  deleteThirdPartyById(@RequestParam("id") Integer id);


    /**
     * 酒店关联的第三方平台信息新增
     * @param businessThirdParty
     * @return
     */
    @PostMapping("/business/thirdparty")
    void insertThirdParty(@RequestBody BusinessThirdParty businessThirdParty);

    /**
     * 获取酒店启用的退订原因
     * @param businessId
     * @return
     */
    @GetMapping("/business/unorderreason")
    List<BusinessUnorderReason> getReasonList(@RequestParam("businessId")Integer businessId);


    @GetMapping(value = "/business/tag/all")
    Map allTag(@RequestParam("businessId") Integer businessId);

    @PostMapping(value = "/business/tag/edit")
    Boolean tagEdit(@RequestParam("businessId")Integer businessId, @RequestParam("tag") String tag);
}
