package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessOrderStatisticsDay;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.resv.dto.BusinessMonthDataDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ConsumptionFrequencyDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.RepeatVipDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2018-12-20
 */
public interface BusinessOrderStatisticsDayMapper extends BaseMapper<BusinessOrderStatisticsDay> {

    List<BusinessOrderStatisticsDay> getYesterdayBusninessData(Integer businessId);

    Integer countNewVip(@Param("businessId") Integer businessId,@Param("mealTypeId")  Integer mealTypeId);

    Integer countRepeatVip(@Param("businessId")Integer businessId,@Param("mealTypeId") Integer mealTypeId);

    BusinessMonthDataDTO getBusinessMonthdata(@Param("businessId")Integer businessId, @Param("dataMonth")String dataMonth);

    List<ConsumptionFrequencyDTO> getConsumptionFrequency(@Param("businessId")Integer businessId, @Param("dataMonth")String dataMonth);

    RepeatVipDTO getRepeatVipByMonth(@Param("businessId") Integer businessId,@Param("dataMonth")  String dataMonth);
}
