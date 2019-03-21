package com.zhidianfan.pig.yd.moduler.resv.controller;




import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.resv.bo.DeskBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.DeskOrderBo;
import com.zhidianfan.pig.yd.moduler.resv.dto.DeskOrderDTO;
import com.zhidianfan.pig.yd.moduler.resv.enums.OrderStatus;
import com.zhidianfan.pig.yd.moduler.resv.service.OrderService;
import com.zhidianfan.pig.yd.moduler.resv.service.TableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;


/**
 * 桌位相关
 * @author LJH
 * @version 1.0
 * @Date 2018/9/19 13:16
 */
@Api("桌位")
@RestController
@RequestMapping("/desk")
public class DeskController {


    @Resource
    private OrderService  orderService;

    @Resource
    private TableService tableService;


    /**
     * 所有桌位
     * @param businessId 酒店id
     * @param status
     * @return
     */
    @GetMapping(value = "/desks",params = {"businessId","status"})
    public ResponseEntity desks(Integer businessId,String status){

        List<DeskBo> tables = tableService.deskList(businessId, status);
        return ResponseEntity.ok(tables);
    }


    /**
     * 订单查询（桌位相关）
     * @param deskOrderDTO
     * @return
     */
    @ApiOperation("订单查询")
    @GetMapping("/orders")
    public ResponseEntity orders(@Valid DeskOrderDTO deskOrderDTO){

        List<DeskOrderBo> resvOrders = orderService.orderSearch(deskOrderDTO);
        return ResponseEntity.ok(resvOrders);
    }


    /**
     * 历史订单查询（桌位相关）
     * @param deskOrderDTO
     * @return
     */
    @ApiOperation("历史订单查询")
    @GetMapping("/historyOrders")
    public ResponseEntity historyOrders(DeskOrderDTO deskOrderDTO){

        Page<DeskOrderBo> resvOrders = orderService.historyOrders(deskOrderDTO);
        return ResponseEntity.ok(resvOrders);
    }


    /**
     * 超时订单
     * @param deskOrderDTO
     * @return
     */
    @GetMapping("/timeoutOrders")
    public ResponseEntity timeoutOrders(@Valid DeskOrderDTO deskOrderDTO){

        deskOrderDTO.setStatus(OrderStatus.RESERVE.code);
        List<DeskOrderBo> resvOrders = orderService.timeoutOrderSearch(deskOrderDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("count",resvOrders.size());
        map.put("list",resvOrders);
        return ResponseEntity.ok(map);
    }


    /**
     *
     */
    @ApiOperation(value="查询一个酒店某日期某餐别某区域,就餐桌位空闲,预定,入座的数量")
    @PostMapping("/deskstatus")
    public ResponseEntity deskStatus(@RequestBody DeskOrderDTO deskOrderDTO){

        //获取当前日期,餐别,区域的桌位状态数量
        Map<String, Integer> deskStatusMap =  tableService.deskStatus(deskOrderDTO);

        return ResponseEntity.ok(deskStatusMap);
    }



}
