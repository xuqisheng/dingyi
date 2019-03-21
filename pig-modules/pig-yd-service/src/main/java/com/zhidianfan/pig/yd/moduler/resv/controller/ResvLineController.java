package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvLine;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.dto.LineDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.LineInfoDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.LineQueryDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.LineTableDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.ResvLineService;
import com.zhidianfan.pig.yd.moduler.sms.bo.message.MessageResultBO;
import com.zhidianfan.pig.yd.moduler.sms.dto.LineMessageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zhidianfan.pig.yd.moduler.resv.controller.AddOrderController.processResult;

/**
 * 排队相关接口
 *
 * @Author: huzp
 * @Date: 2018/9/25 9:53
 */
@RestController
@RequestMapping("/resvline")
@Api(value = "排队")
public class ResvLineController {

    @Autowired
    private ResvLineService resvLineService;


    /**
     * 新增排队订单
     *
     * @return
     */
    @PostMapping("/lineorder")
    public ResponseEntity lineOrder(@Valid @RequestBody LineDTO lineDTO) {

        String lineOrderNo = resvLineService.insertLineOrder(lineDTO);


        Tip tip = (lineOrderNo != null ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        //发送排队短信,排队短信生成成功 ,且Issendmsg 为1

        if (tip instanceof SuccessTip && (lineDTO.getIssendmsg() == 1)) {
            try {
                MessageResultBO messageResultBO = resvLineService.sendLineOrderMessage(lineOrderNo);
                if (null != messageResultBO) {
                    return processResult(messageResultBO, tip);
                }
            }catch (Exception e){
                e.printStackTrace();
                SuccessTip successTip = new SuccessTip();
                successTip.setCode(200);
                successTip.setMsg("排队成功,但短信发送失败");
                return  ResponseEntity.ok(successTip);
            }
        }



        return ResponseEntity.ok(tip);
    }

    /**
     * 修改排队状态为入座 1 排队转入座 2 退订
     *
     * @param lineQueryDTO lineNo 排队号,status 排队状态
     * @return
     */
    @PostMapping("/linestatus")
    public ResponseEntity lineStatus(@RequestBody LineQueryDTO lineQueryDTO) {

        boolean flag = resvLineService.updateStatus(lineQueryDTO);
        Tip tip = (flag ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }


    /**
     * 修改排队订单信息
     *
     * @return
     */
    @ApiOperation(value="排队订单信息修改", notes="根据排队号修改排队订单")
    @PostMapping("/lineinfo")
    public ResponseEntity lineInfo( @RequestBody LineInfoDTO lineInfoDTO) {

        //根据排队号更新排队信息
        Boolean b = resvLineService.updateLineInfo(lineInfoDTO);

        Tip tip = (b != null ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }



    /**
     * 排队人数查询
     *
     * @param lineQueryDTO
     * @return
     */
    @GetMapping("queueCount")
    public ResponseEntity queueCount(@Valid LineQueryDTO lineQueryDTO) {

        Integer integer = resvLineService.queueCount(lineQueryDTO);
        Map<String, Object> result = new HashMap<>();
        result.put("count", integer);
        return ResponseEntity.ok(result);
    }





    /**
     * 指定条件排队订单查询 (客户排队预订单查询 / 指定日期排队预订单查询  )
     *
     * @param lineQueryDTO
     * @return
     */
    @GetMapping("conditionQueueOrder")
    public ResponseEntity conditionQueueOrder(@Valid LineQueryDTO lineQueryDTO) {

        List<ResvLine> resvLines = resvLineService.conditionQueueOrder(lineQueryDTO);
        return ResponseEntity.ok(resvLines);

    }

    /**
     * 指定条件排队订单查询  (排队客户查询 )
     *condition
     * @param lineQueryDTO
     * @return
     */
    @GetMapping("conditionQueueOrderPage")
    public ResponseEntity conditionQueueOrderPage(@Valid LineQueryDTO lineQueryDTO) {


        Page<ResvLine> resvLinePage = resvLineService.conditionQueueOrderPage(lineQueryDTO);
        return ResponseEntity.ok(resvLinePage);
    }


    /**
     * 新版门店后台排队接口查询
     */
    @ApiOperation(value="新版门店后台排队查询", notes="根据条件查询排队订单")
    @PostMapping("/condition")
    public ResponseEntity conditionFindLineOrders(@RequestBody LineQueryDTO lineQueryDTO) {

        Page<LineTableDTO> resvLineOrders = resvLineService.conditionFindLineOrders(lineQueryDTO);

        return ResponseEntity.ok(resvLineOrders);
    }


    /**
     * 门店后台排队订单导出
     *
     * @param lineQueryDTO
     */
    @ApiOperation(value="新版门店后台排队订单导出", notes="根据条件导出排队订单excel")
    @GetMapping("/downloadexcel/line")
    public void downloadLineExcel(@Valid LineQueryDTO lineQueryDTO) {


        //查询出所有符合条件的排队订单数据
        List<LineTableDTO> records = resvLineService.excelConditionFindLineOrders(lineQueryDTO);

        //下载excel
        resvLineService.downloadexcel(records);
    }


    /**
     * 安卓电话机发送排队催促短信
     * @param lineMessageDTO
     * @return
     */
    @PostMapping("/lineurge")
    public ResponseEntity lineUrgeMessage(@RequestBody LineMessageDTO lineMessageDTO) {

        Tip tip = SuccessTip.SUCCESS_TIP;

        MessageResultBO messageResultBO;
        try {
            messageResultBO = resvLineService.sendLineUrgeMessage(lineMessageDTO);
            if (null != messageResultBO) {
                return processResult(messageResultBO, tip);
            }
        }catch (Exception e){
            e.printStackTrace();
            ErrorTip errorTip = new ErrorTip();
            errorTip.setCode(500);
            errorTip.setMsg("发送排队催促短信失败");
            return  ResponseEntity.ok(errorTip);
        }
       return ResponseEntity.ok(ErrorTip.ERROR_TIP);
    }

}
