package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xiaoleilu.hutool.date.DateUtil;
import com.zhidianfan.pig.common.util.PageFactory;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvLine;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.IResvLineService;
import com.zhidianfan.pig.yd.moduler.resv.dto.*;
import com.zhidianfan.pig.yd.moduler.sms.bo.message.MessageResultBO;
import com.zhidianfan.pig.yd.moduler.sms.dto.LineMessageDTO;
import com.zhidianfan.pig.yd.moduler.sms.service.MessageService;
import com.zhidianfan.pig.yd.utils.ExcelUtil;
import com.zhidianfan.pig.yd.utils.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 排队service
 *
 * @Author: huzp
 * @Date: 2018/9/21 16:25
 */
@Service
@Transactional
public class ResvLineService {

    @Autowired
    private IResvLineService iResvLineService;

    @Autowired
    private VipService vipService;

    @Autowired
    private AddOrderService addOrderService;

    //sms模块下的 MessageService
    @Autowired
    private MessageService messageService;

    /**
     * 更新排队信息,排队转入座订单
     *
     * @return
     */
    public boolean updateLine(AddOrderDTO addOrderDTO) {


        Tip tip = addOrderService.inserOrder(addOrderDTO);

        if (tip instanceof ErrorTip) {
            return false;
        }


        ResvLine resvLine = new ResvLine();

        resvLine.setBatchNo(addOrderDTO.getBatchNo());
        resvLine.setUpdatedAt(new Date());
        //状态设置为入座
        resvLine.setStatus(1);

        resvLine.setAndroidUserId(addOrderDTO.getAndroidUserId());
        resvLine.setAndroidUserName(addOrderDTO.getAndroidUserName());

        //更新状态为入座
        boolean updateFalg = iResvLineService.update(resvLine, new EntityWrapper<ResvLine>().
                eq("line_no", addOrderDTO.getLineOrderNo()));

        return updateFalg;

    }

    /**
     * 生成排队信息
     *
     * @return
     */
    public String insertLineOrder(LineDTO lineDTO) {

        Vip vip = new Vip();
        BeanUtils.copyProperties(lineDTO, vip);
        //不更新顾客remark字段
        vip.setRemark(null);
        //更新或新建 排队肯定有手机号码
        vipService.updateOrInsertVip(vip);

        //查询Vip id
        Vip vipInfo = vipService.getBasicVipInfo(vip.getBusinessId(), vip.getVipPhone());

        ResvLine resvLine = new ResvLine();

        //设置推荐到店时间格式为 年月日 时分
        Date resvDate = lineDTO.getResvDate();
        String resvTime = lineDTO.getResvTime();
        String format = DateUtil.format(resvDate, "yyyy-MM-dd");
        resvTime = format + " " + resvTime;
        lineDTO.setResvTime(resvTime);

        //复制相同属性到bean
        BeanUtils.copyProperties(lineDTO, resvLine);

        //设置排队单号
        String lineOrderNo = IdUtils.makeOrderNo();
        resvLine.setLineNo(lineOrderNo);
        resvLine.setResvDate(resvDate);


        //查询当前排队序号
        int line_sort = iResvLineService.selectCount(new EntityWrapper<ResvLine>()
                .eq("resv_date", resvDate)
                .eq("meal_type_id", lineDTO.getMealTypeId())
                .eq("business_id", lineDTO.getBusinessId()));

        //设置为VIP ID
        resvLine.setVipId(vipInfo.getId());
        //为当前排队号加1
        resvLine.setLineSort(line_sort + 1);
        //设置状态 为排队
        resvLine.setStatus(0);
        //设置订单生成时间
        resvLine.setCreatedAt(new Date());

        boolean insert = iResvLineService.insert(resvLine);

        if(insert){
            return lineOrderNo;
        }

        return null;
    }

    /**
     * 更新排队状态 1 为入座  2 为取消排队
     *
     * @return
     */
    public boolean updateStatus(LineQueryDTO lineQueryDTO) {

        ResvLine resvLine = new ResvLine();
        resvLine.setUpdatedAt(new Date());
        //状态设置为成功
        resvLine.setStatus(lineQueryDTO.getStatus());

        resvLine.setAndroidUserId(lineQueryDTO.getAndroidUserId());
        resvLine.setAndroidUserName(lineQueryDTO.getAndroidUserName());

        boolean updateFalg = iResvLineService.update(resvLine, new EntityWrapper<ResvLine>().
                eq("line_no", lineQueryDTO.getLineNo()));

        return updateFalg;
    }


    /**
     * 更新排队信息
     */
    public boolean updateLineInfo(LineInfoDTO lineInfoDTO) {

        ResvLine resvLine = new ResvLine();

        BeanUtils.copyProperties(lineInfoDTO,resvLine);

        boolean flag = iResvLineService.update(resvLine, new EntityWrapper<ResvLine>().
                eq("line_no", lineInfoDTO.getLineNo()));

        return  flag;
    }



    /**
     * 排队客户数量查询
     *
     * @param lineQueryDTO
     * @return
     */
    public Integer queueCount(LineQueryDTO lineQueryDTO) {
        ResvLine resvLine = new ResvLine();
        BeanUtils.copyProperties(lineQueryDTO, resvLine);
        int count = iResvLineService.selectCount(new EntityWrapper<>(resvLine));
        return count;
    }


    /**
     * 排队订单查询  有分页  有排序
     *
     * @param lineQueryDTO
     * @return
     */
    public Page<ResvLine> conditionQueueOrderPage(LineQueryDTO lineQueryDTO) {

        //基础条件
        ResvLine resvLine = new ResvLine();
        BeanUtils.copyProperties(lineQueryDTO, resvLine);
        EntityWrapper<ResvLine> resvLineEntityWrapper = new EntityWrapper<>(resvLine);


        //关键字(手机号 姓名 桌位名称)
        String keyword = lineQueryDTO.getSearch();
        if (StringUtils.isNotBlank(keyword)) {
            resvLineEntityWrapper.andNew();
            resvLineEntityWrapper.like("vip_phone", keyword);
            resvLineEntityWrapper.or();
            resvLineEntityWrapper.like(" vip_name", keyword);
            resvLineEntityWrapper.or();
            resvLineEntityWrapper.like(" table_name", keyword);
        }

        //排序
        String orderByField = lineQueryDTO.getOrderByField();
        if (orderByField != null) {
            resvLineEntityWrapper.orderBy(orderByField, true);
        }

        //分页
        Page<ResvLine> page = new PageFactory().defaultPage();
        Page<ResvLine> resvLinePage = iResvLineService.selectPage(page, resvLineEntityWrapper);
        return resvLinePage;
    }


    /**
     * 客户排队预订单查询 / 指定日期排队预订单查询
     *
     * @param lineQueryDTO
     * @return
     */
    public List<ResvLine> conditionQueueOrder(LineQueryDTO lineQueryDTO) {

        //设置为正在排队的
        lineQueryDTO.setStatus(0);

        ResvLine resvLine = new ResvLine();
        BeanUtils.copyProperties(lineQueryDTO, resvLine);
        EntityWrapper<ResvLine> resvLineEntityWrapper = new EntityWrapper<>(resvLine);

        return iResvLineService.selectList(resvLineEntityWrapper);
    }

    public Page<LineTableDTO> conditionFindLineOrders(LineQueryDTO lineQueryDTO) {

        Page<LineTableDTO> page = new PageFactory().defaultPage();

        iResvLineService.conditionFindVips(page, lineQueryDTO);

        return page;
    }


    /**
     *
     * @param lineQueryDTO 查询条件
     * @return 查询需要导出的数据
     */
    public List<LineTableDTO> excelConditionFindLineOrders(LineQueryDTO lineQueryDTO) {

        List<LineTableDTO> resvLines = iResvLineService.excelConditionFindLineOrders(lineQueryDTO);

        return resvLines;
    }

    /**
     * 导出excel 下载
     *
     * @param records 返回下载的数据
     */
    public void downloadexcel(List<LineTableDTO> records) {

        String sign = "resvline";

        ExcelUtil.ListExport2Excel(sign, records);


    }

    /**
     * 排队发送短息
     * @param lineOrderNo
     * @return
     */
    public MessageResultBO sendLineOrderMessage(String lineOrderNo) {


        ResvLine resvLine = iResvLineService.selectOne(new EntityWrapper<ResvLine>().eq("line_no", lineOrderNo));

        LineMessageDTO lineMessageDTO =  new LineMessageDTO();
        lineMessageDTO.setLineNo(resvLine.getLineNo());
        lineMessageDTO.setPhone(resvLine.getVipPhone());

        MessageResultBO messageResultBO = messageService.sendLineMessage(lineMessageDTO);
        return messageResultBO;
    }


    /**
     * 发送排队催促短信
     * @param lineMessageDTO
     * @return
     */
    public MessageResultBO sendLineUrgeMessage(LineMessageDTO lineMessageDTO) {

        MessageResultBO messageResultBO = messageService.sendLineUrgeMessage(lineMessageDTO);
        return messageResultBO;

    }
}
