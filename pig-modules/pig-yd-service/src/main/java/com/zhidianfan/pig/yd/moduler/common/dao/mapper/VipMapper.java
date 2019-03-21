package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipClass;
import com.zhidianfan.pig.yd.moduler.resv.dto.StatisticsVipDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipConditionCountDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipInfoDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipTableDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sherry
 * @since 2018-08-16
 */
public interface VipMapper extends BaseMapper<Vip> {


    /**
     * 查找指定预定次数的客户数量
     * @param businessId
     * @param orderTime 订单次数 附带符号(>30)
     * @param status 订单状态 可以为空
     * @return
     */
    int findVipNumByOrderTime(@Param("businessId") Integer businessId, @Param("orderTime") String orderTime,@Param("status") String status,@Param("symbol") Integer symbol);


    /**
     * 查找最近预定时间的客户数量
     * @param businessId
     * @param orderDate
     * @return
     */
    int findVipNumByOrderDate(@Param("businessId") Integer businessId, @Param("orderDate") String orderDate,@Param("symbol") Integer symbol);


    /**
     * 查找指定消费金额的客户数量
     * @param businessId
     * @param expense
     * @return
     */
    int findVipNumByAVGExpense(@Param("businessId") Integer businessId,@Param("expense") String expense,@Param("symbol") Integer symbol);

    /**
     * 查询用户列表（带统计信息）
     * @param businessId
     * @param queryVal
     * @return
     */
    List<StatisticsVipDTO> findStatisticsViplist(Page page, @Param("businessId") Integer businessId, @Param("queryVal") String queryVal);

    List<VipTableDTO> conditionFindVips(Page<VipTableDTO> page, VipInfoDTO vipInfoDTO);

    void updateVipClassNULL(VipClass vipClass);

    List<VipTableDTO> excelConditionFindVips(VipInfoDTO vipInfoDTO);

    Integer excelInsertVIPInfo(@Param("vips")List<Vip> vips);

    Integer getNewVipNum(Integer businessId);

    List<VipTableDTO> getConditionVipPage(Page<VipTableDTO> page, VipConditionCountDTO vipConditionCountDTO);

    List<Vip> getPastBirthdayVip();

    List<Vip> selectBirthType1();

    List<Vip> selectBirthType2();

    List<Vip> selectBirthType3();

    List<Vip> selectBirthLunarType1();

    List<Vip> selectBirthLunarType2();

    List<Vip> selectBirthType4();

    List<Vip> selectBirthLunarType3();

    List<Vip> selectBirthByBusinessId(@Param("bid") Integer bid);
}
