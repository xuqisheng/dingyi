package com.zhidianfan.pig.yd.moduler.common.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.resv.bo.*;
import com.zhidianfan.pig.yd.moduler.resv.dto.*;
import com.zhidianfan.pig.yd.moduler.resv.qo.AllResvOrderQO;
import com.zhidianfan.pig.yd.moduler.resv.qo.LockTablQO;
import com.zhidianfan.pig.yd.moduler.resv.qo.MessageOrderQO;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzp
 * @since 2019-01-15
 */
public interface IResvOrderAndroidService extends IService<ResvOrderAndroid> {

    MessageOrderBO getMessageOrder(MessageOrderQO messageOrderQO);


    List<OrderBO> getAllResvOrders(AllResvOrderQO allResvOrderQO);

    void deleteResvOrders(ResvOrderDTO resvOrderDTO);


    /**
     * 桌位订单
     * @param deskOrderDTO
     * @return
     */
    List<DeskOrderBo> findDeskOrders(Page page, DeskOrderDTO deskOrderDTO);


    List<DeskOrderBo> findOrders(DeskOrderDTO deskOrderDTO);

    Integer  selectResvTimes(Integer id, String status);

    Map<String, String> selectTableAndPeopleNum(Integer id, String status);

    Date selectLastEatTime(Integer id, String status);

    boolean updateStatusByOrder(List<String> resvOrders , EditStatusDTO editStatusDTO);

    boolean updateConfirmByOrder(List<String> resvOrders, String confirm);

    void conditionQueryResvOrder(Page<ResvTableOrder> page, ResvOrderQueryDTO resvOrderQueryDTO);


    List<ResvTableOrder> excelConditionFindResvOrders(ResvOrderQueryDTO resvOrderQueryDTO);

    String  getorderSucNum(Integer businessId);

    Map<String, Object> getYesterdayData(Integer businessId);

    /**
     * 锁台记录分页查询
     */
    void conditionQueryLockRecord(Page<LockTablDTO> page, LockTablQO lockTablQO);

    boolean checkoutBills(CheckoutBillDTO checkoutBillDTO,Integer userId,String userName);

    String getpaySum(Integer businessId);

    List<Integer> getYesterdayBusniness();


    List<Map<String, Integer>> getOrderDistribution(Integer businessId, String calDate);

    List<Map<String, Object>> getAllWeChatThirdOrder( LocalDateTime localDateTime);

    void updateAndroidOrderStatus1TO2(Integer intervalNum);

    void updateAndroidOrderStatus1TO4(Integer intervalNum);

    void updateAndroidOrderStatus2TO3(Integer intervalNum);

    List<DeskOrderBo> selectListWithAllergen(String batchNo);

    ResvOrderAndroid selectBrandLastEatTime(String phone, Integer brandId);


}
