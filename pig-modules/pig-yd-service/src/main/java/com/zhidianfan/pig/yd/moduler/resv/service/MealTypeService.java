package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.MealType;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import com.zhidianfan.pig.yd.moduler.common.service.IMealTypeService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderAndroidService;
import com.zhidianfan.pig.yd.moduler.resv.dto.MealTypeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MealTypeService {

    @Autowired
    IMealTypeService iMealTypeService;

    @Autowired
    IBusinessService iBusinessService;

    @Autowired
    IResvOrderAndroidService iResvOrderAndroidService;

    /**
     * 查询新的mealtype
     *
     * @param id 酒店id
     * @return
     */
    public List<MealType> getNewMealTypeByBusinessId(String id) {

        //查询酒店id 是否有mealtype
        List<MealType> mealTypes = iMealTypeService.selectList(new EntityWrapper<MealType>()
                .eq("business_id", id)
                .eq("isnew", "1")
                );


        //1.为空的话,创建再给他查询出来
        if (mealTypes.size() == 0) {
            Business business = iBusinessService.selectById(id);
            //转变原先的餐别为新餐别
            List<MealType> commomMealType = createCommomMealType(business.getId(), business.getBusinessName());

            iMealTypeService.insertBatch(commomMealType);
        }

        List<MealType> mealTypeList = iMealTypeService.selectList(new EntityWrapper<MealType>()
                .eq("business_id", id).eq("isnew", "1"));

        return mealTypeList;
    }


    /**
     * 查询以前的mealtype
     *
     * @param id 酒店id
     * @return
     */
    public List<MealType> selectOldMealTypeByBusinessId(String id) {
        List<MealType> mealTypeList = iMealTypeService.selectList(new EntityWrapper<MealType>()
                .eq("business_id", id).eq("isnew", "0"));
        return mealTypeList;
    }


    /**
     * 修改mealtype
     *
     * @param mealTypeDTOList
     * @return
     */
    public Object editMealTypeInfo(List<MealTypeDTO> mealTypeDTOList) {

        //判断停用的餐别是否有正在进行的订单,有的话不允许停用,必须先转移或者订单餐别
        //1.查询正在启用的餐别
        List<MealType> mealTypeList = iMealTypeService.selectList(new EntityWrapper<MealType>()
                .eq("business_id", mealTypeDTOList.get(0).getBusinessId()).eq("isnew", "1").eq("status",1));


        Map<String,List<ResvOrderAndroid>> resvOrdersMap = Maps.newHashMap();

        //对比是否有正在启用的餐别被停用
        for (MealTypeDTO mealTypeDTO : mealTypeDTOList) {
            //餐别 状态为0 再去对比
            if("0".equals(mealTypeDTO.getStatus())){
                for (MealType mealType: mealTypeList) {
                    //id 相同说明是同一 餐别
                    if(mealTypeDTO.getId().equals(mealType.getId())){
                        //查询该餐别是否有还未完成订单
                        List<ResvOrderAndroid> resvOrders = iResvOrderAndroidService.selectList(new EntityWrapper<ResvOrderAndroid>()
                                .eq("meal_type_id", mealType.getId())
                                .andNew()
                                .eq("status", 1).or().eq("status", 2));
                        //存在未完成订单则需要不进行餐别修改
                        if (resvOrders.size() != 0) {
                            resvOrdersMap.put(mealType.getMealTypeName(),resvOrders);
                        }
                    }
                }
            }
        }
        //如果餐别不为空
        if(resvOrdersMap.size() !=0){
            return  resvOrdersMap;
        }


        //如果不存在未完成订单则直接修改餐时
        MealType mealType = new MealType();

        for (MealTypeDTO mealTypeDTO : mealTypeDTOList) {
            BeanUtils.copyProperties(mealTypeDTO, mealType);
            iMealTypeService.updateById(mealType);
        }

        //一切操作顺利则返回成功
        return SuccessTip.SUCCESS_TIP;
    }


    /**
     * 转换酒店原先的餐别
     * @param id
     * @param businessName
     * @return
     */
    private List<MealType> createCommomMealType(Integer id, String businessName) {
        
        Map<String,MealType > commonMealTypeMap = Maps.newLinkedHashMap();
        
        Date date = new Date();
        //pig -- sys_dict表 的config 以及type code
        MealType mealType1 = new MealType(id, "早餐", businessName, "0", "001", date, "00:00", "00:00", 10, 1);
        MealType mealType2 = new MealType(id, "上午茶", businessName, "0", "005", date, "00:00", "00:00", 14, 1);
        MealType mealType3 = new MealType(id, "中餐", businessName, "0", "002", date, "00:00", "00:00", 11, 1);
        MealType mealType4 = new MealType(id, "下午茶", businessName, "0", "006", date, "00:00", "00:00", 15, 1);
        MealType mealType5 = new MealType(id, "晚餐", businessName, "0", "003", date, "00:00", "00:00", 12, 1);
        MealType mealType6 = new MealType(id, "夜宵", businessName, "0", "004", date, "00:00", "00:00", 13, 1);

        //存入map
        commonMealTypeMap.put(mealType1.getConfigId().toString(),mealType1);
        commonMealTypeMap.put(mealType2.getConfigId().toString(),mealType2);
        commonMealTypeMap.put(mealType3.getConfigId().toString(),mealType3);
        commonMealTypeMap.put(mealType4.getConfigId().toString(),mealType4);
        commonMealTypeMap.put(mealType5.getConfigId().toString(),mealType5);
        commonMealTypeMap.put(mealType6.getConfigId().toString(),mealType6);

        //查询出原先生效的餐别
        List<MealType> mealTypeList = iMealTypeService.selectList(new EntityWrapper<MealType>()
                .eq("business_id", id)
                .eq("isnew", "0")
                .eq("status",1)
                .ne("config_id",0));

        //在转变为新得餐别
        for (MealType m: mealTypeList) {

            MealType mealType = commonMealTypeMap.get(m.getConfigId().toString());

            if(null == mealType) continue;
            //设置为 启用
            mealType.setStatus("1");
            //复制开始时间和结束时间
            mealType.setResvStartTime(m.getResvStartTime());
            mealType.setResvEndTime(m.getResvEndTime());
        }


        List<MealType> commonMealType = new ArrayList<>();

        for (MealType m:commonMealTypeMap.values()) {
            commonMealType.add(m);
        }


        return commonMealType;
    }


}
