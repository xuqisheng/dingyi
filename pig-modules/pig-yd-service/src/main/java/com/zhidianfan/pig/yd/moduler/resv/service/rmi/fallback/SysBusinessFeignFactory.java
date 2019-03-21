package com.zhidianfan.pig.yd.moduler.resv.service.rmi.fallback;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.SysBusinessFeign;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/28
 * @Modified By:
 */
@Component
public class SysBusinessFeignFactory implements FallbackFactory<SysBusinessFeign> {

    private Logger log = LoggerFactory.getLogger(getClass());


    @Override
    public SysBusinessFeign create(Throwable throwable) {
        log.error("SysBusinessFeign调用异常：{}", throwable.getMessage());
        return new SysBusinessFeign() {
            @Override
            public Business getBusinessInfo(Integer id) {
                log.error("调用异常 {}.{} ", "SysBusinessFeign", "getBusinessInfo");
                return null;
            }

            @Override
            public List<MealType> getMealTypeInfo(Integer id) {
                log.error("调用异常 {}.{} ", "SysBusinessFeign", "getMealTypeInfo");
                return null;
            }

            @Override
            public List<TableArea> getTableAreaInfo(Integer id) {
                log.error("调用异常 {}.{} ", "SysBusinessFeign", "getTableAreaInfo");
                return null;
            }

            @Override
            public List<BusinessThirdParty> getThirdPartyInfo(Integer id) {
                log.error("调用异常 {}.{} ", "SysBusinessFeign", "getThirdPartyInfo");
                return null;
            }

            @Override
            public boolean changeDefMealsNum(Business business) {
                log.error("调用异常 {}.{} ", "SysBusinessFeign", "changeDefMealsNum");
                return false;
            }

            @Override
            public boolean changeOrderExemptionVerifyCheck(Business business) {
                log.error("调用异常 {}.{} ", "SysBusinessFeign", "changeOrderExemptionVerifyCheck");
                return false;
            }

            @Override
            public void deleteThirdPartyById(Integer id) {
                log.error("调用异常 {}.{} ", "SysBusinessFeign", "deleteThirdPartyById");

            }

            @Override
            public void insertThirdParty(BusinessThirdParty businessThirdParty) {
                log.error("调用异常 {}.{} ", "SysBusinessFeign", "insertThirdParty");

            }

            @Override
            public List<BusinessUnorderReason> getReasonList(Integer businessId) {
                log.error("调用异常 {}.{} ", "SysBusinessFeign", "getReasonList");
                return null;
            }

            @Override
            public Map allTag(Integer businessId) {
                log.error("调用异常 {}.{} ", "allTag",businessId);
                return new HashMap<>();
            }

            @Override
            public Boolean tagEdit(Integer businessId, String tag) {
                log.error("调用异常 {}.{} ", "tagEdit",businessId);
                return Boolean.FALSE;
            }

        };
    }

}
