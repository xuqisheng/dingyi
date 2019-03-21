package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipClass;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.VipMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IVipService;
import com.zhidianfan.pig.yd.moduler.resv.dto.StatisticsVipDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipConditionCountDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipInfoDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipTableDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-08-16
 */
@Service
public class VipServiceImpl extends ServiceImpl<VipMapper, Vip> implements IVipService {


    @Override
    public int findVipNumByOrderTime(Integer businessId, String orderTime,String status,Integer symbol) {
        return this.baseMapper.findVipNumByOrderTime(businessId,orderTime,status,symbol);
    }

    @Override
    public int findVipNumByOrderDate(Integer businessId, String orderDate,Integer symbol) {
        return this.baseMapper.findVipNumByOrderDate(businessId,orderDate, symbol);
    }

    @Override
    public int findVipNumByAVGExpense(Integer businessId, String expense,Integer symbol) {
        return this.baseMapper.findVipNumByAVGExpense(businessId,expense, symbol);
    }

    @Override
    public List<StatisticsVipDTO> findStatisticsViplist(Page<StatisticsVipDTO> page, Integer businessId, String queryVal) {
        return this.baseMapper.findStatisticsViplist(page, businessId, queryVal);
    }
    public void conditionFindVips(Page<VipTableDTO> page, VipInfoDTO vipInfoDTO) {

        List<VipTableDTO> vipInfoDTOS = this.baseMapper.conditionFindVips(page, vipInfoDTO);
        page.setRecords(vipInfoDTOS);
    }

    @Override
    public void updateVipClassNULL(VipClass vipClass) {
        baseMapper.updateVipClassNULL(vipClass);
    }

    @Override
    public List<VipTableDTO> excelConditionFindVips(VipInfoDTO vipInfoDTO) {
        List<VipTableDTO> vipInfoDTOS =  baseMapper.excelConditionFindVips(vipInfoDTO);
        return vipInfoDTOS;
    }

    @Override
    public Integer excelInsertVIPInfo(List<Vip> vips) {
        Integer num = baseMapper.excelInsertVIPInfo(vips);
        return num;
    }

    @Override
    public Integer getNewVipNum(Integer businessId) {
        return baseMapper.getNewVipNum(businessId);
    }

    @Override
    public void getConditionVipPage(Page<VipTableDTO> page, VipConditionCountDTO vipConditionCountDTO) {
        List<VipTableDTO> ConditionVip =  baseMapper.getConditionVipPage(page,vipConditionCountDTO);
        page.setRecords(ConditionVip);
    }

    @Override
    public List<Vip> getPastBirthdayVip() {
        return baseMapper.getPastBirthdayVip();
    }

    @Override
    public List<Vip> selectBirthType1() {
        return baseMapper.selectBirthType1();
    }

    @Override
    public List<Vip> selectBirthType2() {
        return baseMapper.selectBirthType2();
    }

    @Override
    public List<Vip> selectBirthType3() {
        return baseMapper.selectBirthType3();
    }

    @Override
    public List<Vip> selectBirthLunarType1() {
        return baseMapper.selectBirthLunarType1();
    }

    @Override
    public List<Vip> selectBirthLunarType2() {
        return baseMapper.selectBirthLunarType2();
    }

    @Override
    public List<Vip> selectBirthType4() {
        return baseMapper.selectBirthType4();
    }

    @Override
    public List<Vip> selectBirthLunarType3() {
        return baseMapper.selectBirthLunarType3();
    }

    @Override
    public List<Vip> selectBirthByBusinessId(Integer bid) {
        return baseMapper.selectBirthByBusinessId(bid);
    }


}
