package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.RatingConfig;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderRating;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.IRatingConfigService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderAndroidService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderRatingService;
import com.zhidianfan.pig.yd.moduler.resv.bo.BatchOrderBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.DeskOrderBo;
import com.zhidianfan.pig.yd.moduler.resv.dto.*;
import com.zhidianfan.pig.yd.moduler.resv.qo.LockTablQO;
import com.zhidianfan.pig.yd.moduler.resv.service.OrderService;
import com.zhidianfan.pig.yd.moduler.resv.vo.ResvOrderVO;
import com.zhidianfan.pig.yd.moduler.resv.vo.TableVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * @Author panqianyong
 * @Description 订单删除
 * @Date Create in 2018/9/5
 * @Modified By:
 */
@RestController
@RequestMapping("/resvorder")
@Api(value = "订单")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Resource
    private IResvOrderAndroidService iResvOrderService;

    @Resource
    private IResvOrderRatingService iResvOrderRatingService;

    @Resource
    private IRatingConfigService iRatingConfigService;

    /**
     * 删除普通预定 订单
     *
     * @param
     * @return
     */
    @PostMapping(value = "/delete/orders")
    public ResponseEntity deleteOrders(@RequestBody ResvOrderDTO resvOrderDTO) {

        orderService.deleteOrders(resvOrderDTO);

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }

    /**
     * 删除普通预定 订单
     *
     * @param
     * @return
     */
    @PostMapping(value = "/delete/meetingorders")
    public ResponseEntity deleteMeetingOrders(@RequestBody ResvMeetingOrderDTO resvMeetingOrderDTO) {
        orderService.deleteMeetingOrders(resvMeetingOrderDTO);

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }

    /**
     * 预定订单修改
     *
     * @param reserveOrderEditDTO
     * @return
     */
    @PostMapping(value = "/edit/reserve")
    public ResponseEntity editReserve(@Valid @RequestBody ReserveOrderEditDTO reserveOrderEditDTO) {
        Tip tip = orderService.editReserve(reserveOrderEditDTO);

        return ResponseEntity.ok(tip);
    }


    /**
     * 根据批次号查找订单
     *
     * @param batchNo
     * @return
     */
    @ApiOperation(value="预订单详情查询", notes="根据批次号查询订单")
    @GetMapping(value = "/order/batchNo")
    @ApiImplicitParam(paramType="query", name = "batchNo", value = "订单批次号", dataType = "String")
    public ResponseEntity orderByBatchNo(String batchNo) {


        // 增加订单信息携带过敏源
        List<DeskOrderBo> deskOrderBos = iResvOrderService.selectListWithAllergen(batchNo);

        return ResponseEntity.ok(deskOrderBos);
    }

    /**
     * 新版门店后台预订单查询
     * @param resvOrderQueryDTO
     * @return
     */
    @ApiOperation(value="新版门店后台预订单条件查询", notes="根据查询条件查询订单")
    @PostMapping("/condition")
    public ResponseEntity conditionQueryResvOrder(@RequestBody ResvOrderQueryDTO resvOrderQueryDTO){

        Page<ResvTableOrder> resvOrders = orderService.conditionQueryResvOrder(resvOrderQueryDTO);
        return  ResponseEntity.ok(resvOrders);
    }


    /**
     * 门店后台解锁台记录查询
     */
    @ApiOperation(value="新版门店后台解锁台记录查询", notes="根据条件查询解锁台记录")
    @GetMapping("/locktablerecord")
    public ResponseEntity lockTableRecord ( @Valid LockTablQO lockTablQO){

        Page<LockTablDTO> resvOrders = orderService.conditionQueryLockRecord(lockTablQO);
        return  ResponseEntity.ok(resvOrders);
    }



    /**
     * 门店后台订单导出
     */
    @ApiOperation(value="新版门店后台预订单excel导出", notes="根据查询条件导出订单excel")
    @GetMapping("/downloadexcel/resv")
    public void downloadResvExcel(@Valid ResvOrderQueryDTO resvOrderQueryDTO){


        //查询出所有符合条件的订单数据
        List<ResvTableOrder> records  =orderService.excelConditionFindResvOrders(resvOrderQueryDTO);

        //下载excel
        orderService.downloadexcel(records);


    }


    /**
     * 门店订单操作记录查询
     */
    @ApiOperation(value="新版门店订单操作记录查询")
    @GetMapping("/orderlogs")
    @ApiImplicitParam(paramType="query", name = "batchno", value = "订单批次号", dataType = "String")
    public ResponseEntity orderLogs (@RequestParam("batchno") String batchNo){

        List<ResvOrderLogsDTO> resvOrderLogs =  orderService.getOrderLogsByBatchNo(batchNo);

        return  ResponseEntity.ok(resvOrderLogs);
    }


    /**
     * 根据条件查找订单
     * @param resvOrderDTO
     * @return
     */
    @PostMapping(value = "/order/search")
    public ResponseEntity orderSearch(@RequestBody OrderDTO resvOrderDTO) {

        List<ResvOrderAndroid> resvOrder = orderService.searchOrders(resvOrderDTO);
        return ResponseEntity.ok(resvOrder);
    }



    /**
     * 查询酒店订单
     * @param resvOrderDTO
     * @return
     */
    @GetMapping
    public ResponseEntity resvOrder(ResvOrderDTO resvOrderDTO) {
        EntityWrapper<ResvOrderAndroid> entityWrapper = new EntityWrapper<>(resvOrderDTO);
        if(resvOrderDTO.getIsSeat() == 1){
            entityWrapper.in("status", Arrays.asList(1,2,3));
        }else if(resvOrderDTO.getIsSeat() == 0){
            entityWrapper.eq("status", 1);
        }
        List<ResvOrderAndroid> resvOrders = iResvOrderService.selectList(entityWrapper);
        Map<String, ResvOrderVO> resvOrderVOMap = Maps.newHashMap();
        resvOrders.forEach(resvOrder -> {
            ResvOrderVO resvOrderVO = resvOrderVOMap.get(resvOrder.getBatchNo());
            if (resvOrderVO == null) {
                resvOrderVO = new ResvOrderVO();
                BeanUtils.copyProperties(resvOrder, resvOrderVO);
                if (StringUtils.equals(resvOrderVO.getVipSex(), "男")) {
                    resvOrderVO.setVipSex("先生");
                } else if (StringUtils.equals(resvOrderVO.getVipSex(), "女")) {
                    resvOrderVO.setVipSex("女士");
                } else {
                    resvOrderVO.setVipSex("");
                }
            }
            List<TableVO> tables = resvOrderVO.getTables();
            if (tables == null) {
                tables = Lists.newArrayList();
                resvOrderVO.setTables(tables);
            }
            TableVO tableVO = new TableVO();
            BeanUtils.copyProperties(resvOrder, tableVO);
            resvOrderVO.getTables().add(tableVO);
            resvOrderVOMap.put(resvOrder.getBatchNo(), resvOrderVO);
        });
        return ResponseEntity.ok(resvOrderVOMap.values());
    }



    @ApiOperation("订单评价")
    @PostMapping(value = "/order/rating")
    public ResponseEntity<Tip> orderRating(@Valid OrderRatingDTO bean) {

        boolean tip = orderService.orderRating(bean);
        return ResponseEntity.ok(tip?SuccessTip.SUCCESS_TIP: ErrorTip.ERROR_TIP);
    }


    @ApiOperation("订单评价星级信息")
    @GetMapping(value = "/order/rating/star")
    public ResponseEntity<List<RatingConfig>> orderRatingStar() {

        List<RatingConfig> ratingConfigs = iRatingConfigService.selectList(new EntityWrapper<>());
         return ResponseEntity.ok(ratingConfigs);
    }


    @ApiOperation("订单评价查看")
    @GetMapping(value = "/order/rating/info")
    public ResponseEntity<ResvOrderRating> orderRating(Integer id) {

        ResvOrderRating resvOrderRating = iResvOrderRatingService.selectById(id);
        return ResponseEntity.ok(resvOrderRating);
    }



    @ApiOperation("查询一个客户的上次就餐评价")
    @GetMapping(value = "/order/rating/viplastinfo")
    public ResponseEntity<ResvOrderRating> vipLastInfo(@RequestParam("vipid") Integer vipId) {

        //查询一个客户的上次就餐评价
        ResvOrderRating resvOrderRating = iResvOrderRatingService.selectVipLastInfo(vipId);

        return ResponseEntity.ok(resvOrderRating);
    }



    /**
     * 分类订单查询
     * @param bean
     * @return
     */
    @ApiOperation("分类订单查询")
    @PostMapping(value = "/order/search/status")
    public ResponseEntity<List<BatchOrderBo>> orderSearchStatus(@RequestBody DeskOrderDTO bean) {

        List<BatchOrderBo> orderSearchStatus = orderService.orderSearchStatus(bean);
        return ResponseEntity.ok(orderSearchStatus);
    }


    /**
     * 小程序业绩统计
     */
    @GetMapping(value = "/performanceStatistics")
    public ResponseEntity performanceStatistics(@Valid PerformanceDTO performanceDTO) {

        JSONObject jsonObject = orderService.performanceStatistics(performanceDTO);

        return ResponseEntity.ok(jsonObject);
    }






}


