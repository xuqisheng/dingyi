package com.zhidianfan.pig.yd.moduler.common.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipClass;
import com.zhidianfan.pig.yd.moduler.resv.dto.StatisticsVipDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipConditionCountDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipInfoDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipTableDTO;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sherry
 * @since 2018-08-16
 */
public interface IVipService extends IService<Vip> {

    /**
     * 查找指定预定次数的客户数量
     * @param businessId
     * @param orderTime 订单次数 附带符号(>30)
     * @param status 订单状态 可以为空
     * @return
     */
    int findVipNumByOrderTime( Integer businessId, String orderTime,String status,Integer symbol);


    /**
     * 查找最近预定时间的客户数量
     * @param businessId
     * @param orderDate
     * @return
     */
    int findVipNumByOrderDate( Integer businessId, String orderDate,Integer symbol);


    /**
     * 查找指定消费金额的客户数量
     * @param businessId
     * @param expense
     * @return
     */
    int findVipNumByAVGExpense(Integer businessId,String expense,Integer symbol);

    /**
     * 查询用户列表（带统计信息）
     * @param businessId
     * @return
     */
    List<StatisticsVipDTO> findStatisticsViplist(Page<StatisticsVipDTO> page, Integer businessId, String queryVal);

    void conditionFindVips(Page<VipTableDTO> page, VipInfoDTO vipInfoDTO);

    void updateVipClassNULL(VipClass vipClass);

    List<VipTableDTO> excelConditionFindVips(VipInfoDTO vipInfoDTO);

    Integer excelInsertVIPInfo(List<Vip> vips);

    Integer getNewVipNum(Integer businessId);

    void getConditionVipPage(Page<VipTableDTO> page, VipConditionCountDTO vipConditionCountDTO);

    List<Vip> getPastBirthdayVip();

    List<Vip> selectBirthType1();

    List<Vip> selectBirthType2();

    List<Vip> selectBirthType3();

    List<Vip> selectBirthLunarType1();

    List<Vip> selectBirthLunarType2();

    List<Vip> selectBirthType4();

    List<Vip> selectBirthLunarType3();

    List<Vip> selectBirthByBusinessId(Integer bid);

    List<Vip> getAppUserVipMarking(Set<Integer> vipIds,Integer appUserId);

    List<Vip> getNoAppUserVipMarking(Set<Integer> vipIds);
}
