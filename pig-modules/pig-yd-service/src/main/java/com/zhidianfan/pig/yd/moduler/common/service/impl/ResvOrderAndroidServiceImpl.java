package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvOrderAndroidMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderAndroidService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.resv.bo.DeskOrderBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.MessageOrderBO;
import com.zhidianfan.pig.yd.moduler.resv.bo.OrderBO;
import com.zhidianfan.pig.yd.moduler.resv.bo.PerformanceBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.*;
import com.zhidianfan.pig.yd.moduler.resv.qo.AllResvOrderQO;
import com.zhidianfan.pig.yd.moduler.resv.qo.LockTablQO;
import com.zhidianfan.pig.yd.moduler.resv.qo.MessageOrderQO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2019-01-15
 */
@Service
public class ResvOrderAndroidServiceImpl extends ServiceImpl<ResvOrderAndroidMapper, ResvOrderAndroid> implements IResvOrderAndroidService {


    @Override
    public MessageOrderBO getMessageOrder(MessageOrderQO messageOrderQO) {
        return baseMapper.getMessageOrder(messageOrderQO);
    }

    @Override
    public List<OrderBO> getAllResvOrders(AllResvOrderQO allResvOrderQO) {
        return baseMapper.getAllResvOrders(allResvOrderQO);
    }

    @Override
    public void deleteResvOrders(ResvOrderDTO resvOrderDTO) {
        this.baseMapper.deleteResvOrders(resvOrderDTO);
    }

    @Override
    public List<DeskOrderBo> findDeskOrders(Page page, DeskOrderDTO deskOrderDTO) {
        return baseMapper.findDeskOrders(page, deskOrderDTO);
    }

    @Override
    public List<DeskOrderBo> findOrders(Page page,DeskOrderDTO deskOrderDTO) {
        return baseMapper.findOrders(page,deskOrderDTO);
    }

    @Override
    public Integer selectResvTimes(Integer id, String status) {
        Integer resvTimes = baseMapper.selectResvTimes(id, status);
        return resvTimes;
    }

    @Override
    public Map<String, String> selectTableAndPeopleNum(Integer id, String status) {
        Map<String, String> tableAndPeopleNum = baseMapper.selectTableAndPeopleNum(id, status);
        return tableAndPeopleNum;
    }

    @Override
    public Date selectLastEatTime(Integer id, String status) {
        Date eatTime = baseMapper.selectLastEatTime(id, status);
        return eatTime;
    }

    @Override
    public boolean updateStatusByOrder(List<String> resvOrders, EditStatusDTO editStatusDTO) {
        boolean b = baseMapper.updateStatusByOrder(resvOrders, editStatusDTO);
        return b;
    }

    @Override
    public boolean updateConfirmByOrder(List<String> resvOrders, String confirm) {
        return baseMapper.updateConfirmByOrder(resvOrders, confirm);
    }

    @Override
    public void conditionQueryResvOrder(Page<ResvTableOrder> page, ResvOrderQueryDTO resvOrderQueryDTO) {
        List<ResvTableOrder> resvOrders = this.baseMapper.conditionQueryResvOrder(page, resvOrderQueryDTO);
        page.setRecords(resvOrders);
    }


    @Override
    public List<ResvTableOrder> excelConditionFindResvOrders(ResvOrderQueryDTO resvOrderQueryDTO) {
        List<ResvTableOrder> resvTableOrders = baseMapper.excelConditionFindResvOrders(resvOrderQueryDTO);
        return resvTableOrders;
    }

    @Override
    public String getorderSucNum(Integer businessId) {
        return baseMapper.getorderSucNum(businessId);
    }

    @Override
    public Map<String, Object> getYesterdayData(Integer businessId) {
        return baseMapper.getYesterdayData(businessId);
    }

    @Override
    public void conditionQueryLockRecord(Page<LockTablDTO> page, LockTablQO lockTablQO) {

        List<LockTablDTO> lockTablDTOS = baseMapper.conditionQueryLockRecord(page, lockTablQO);

        page.setRecords(lockTablDTOS);

    }

    @Override
    public boolean checkoutBills(CheckoutBillDTO checkoutBillDTO, Integer userId, String userName) {
        boolean b = baseMapper.checkoutBills(checkoutBillDTO, userId, userName);
        return b;
    }

    @Override
    public String getpaySum(Integer businessId) {
        return baseMapper.getpaySum(businessId);
    }

    @Override
    public List<Integer> getYesterdayBusniness() {
        return baseMapper.getYesterdayBusniness();
    }

    @Override
    public List<Map<String, Integer>> getOrderDistribution(Integer businessId, String calDate) {

        return baseMapper.getOrderDistribution(businessId, calDate);
    }

    @Override
    public List<Map<String, Object>> getAllWeChatThirdOrder(LocalDateTime localDateTime) {
        return baseMapper.getAllWeChatThirdOrder(localDateTime);
    }

    @Override
    public void updateAndroidOrderStatus1TO2(Integer intervalNum) {
        baseMapper.updateAndroidOrderStatus1TO2(intervalNum);
    }

    @Override
    public void updateAndroidOrderStatus1TO4(Integer intervalNum) {
        baseMapper.updateAndroidOrderStatus1TO4(intervalNum);
    }

    @Override
    public void updateAndroidOrderStatus2TO3(Integer intervalNum) {
        baseMapper.updateAndroidOrderStatus2TO3(intervalNum);
    }

    @Override
    public List<DeskOrderBo> selectListWithAllergen(String batchNo) {
        return baseMapper.selectListWithAllergen(batchNo);
    }

    @Override
    public ResvOrderAndroid selectBrandLastEatTime(String phone, Integer brandId) {
        return baseMapper.selectBrandLastEatTime(phone, brandId);
    }




}
