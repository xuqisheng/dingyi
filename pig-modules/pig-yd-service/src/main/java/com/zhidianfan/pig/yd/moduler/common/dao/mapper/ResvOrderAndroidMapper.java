package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.resv.bo.DeskOrderBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.MessageOrderBO;
import com.zhidianfan.pig.yd.moduler.resv.bo.OrderBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.*;
import com.zhidianfan.pig.yd.moduler.resv.qo.AllResvOrderQO;
import com.zhidianfan.pig.yd.moduler.resv.qo.LockTablQO;
import com.zhidianfan.pig.yd.moduler.resv.qo.MessageOrderQO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2019-01-15
 */
public interface ResvOrderAndroidMapper extends BaseMapper<ResvOrderAndroid> {

    /**
     * @param messageOrderQO
     */
    MessageOrderBO getMessageOrder(@Param("message") MessageOrderQO messageOrderQO);

    List<OrderBO> getAllResvOrders(@Param("order") AllResvOrderQO allResvOrderQO);

    void deleteResvOrders(ResvOrderDTO resvOrderDTO);


    List<DeskOrderBo> findDeskOrders(Page page, DeskOrderDTO deskOrderDTO);

    List<DeskOrderBo> findOrders(DeskOrderDTO deskOrderDTO);

    Integer selectResvTimes(@Param("id")Integer id,@Param("status") String status);

    Map<String, String> selectTableAndPeopleNum(@Param("id")Integer id , @Param("status") String status);

    Date selectLastEatTime(@Param("id")Integer id, @Param("status") String status);


    boolean updateStatusByOrder(@Param("resvOrders")List<String> resvOrders, @Param("editStatusDTO") EditStatusDTO editStatusDTO);

    boolean updateConfirmByOrder(@Param("resvOrders")List<String> resvOrders, @Param("confirm") String confirm);

    List<ResvTableOrder> conditionQueryResvOrder(Page<ResvTableOrder> page, ResvOrderQueryDTO resvOrderQueryDTO);

    List<ResvTableOrder> excelConditionFindResvOrders(ResvOrderQueryDTO resvOrderQueryDTO);

    String getorderSucNum(Integer businessId);

    Map<String, Object> getYesterdayData(Integer businessId);

    List<LockTablDTO> conditionQueryLockRecord(Page<LockTablDTO> page, LockTablQO lockTablQO);

    boolean checkoutBills(@Param("checkoutBill") CheckoutBillDTO checkoutBillDTO,
                          @Param("userId") Integer userId,
                          @Param("userName") String userName);

    String getpaySum(Integer businessId);

    List<Integer> getYesterdayBusniness();

    List<Map<String, Integer>> getOrderDistribution(@Param("businessId")Integer businessId, @Param("calDate")String calDate);

    List<Map<String, Object>> getAllWeChatThirdOrder(@Param("resvDate") LocalDateTime localDateTime);

    void updateAndroidOrderStatus1TO2(@Param("intervalNum")Integer intervalNum);

    void updateAndroidOrderStatus1TO4(@Param("intervalNum")Integer intervalNum);

    void updateAndroidOrderStatus2TO3(@Param("intervalNum")Integer intervalNum);

    List<DeskOrderBo> selectListWithAllergen(@Param("batchNo")String batchNo);

    ResvOrderAndroid selectBrandLastEatTime(@Param("phone")String phone,@Param("brandId") Integer brandId);
}
