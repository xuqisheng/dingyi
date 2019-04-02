package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Anniversary;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.dto.AnniversaryDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.CustomerCareDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.AnniversaryService;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: hzp
 * @Date: 2019-03-28 16:04
 * @Description:
 */
@RestController
@RequestMapping("/anniversary")
@Slf4j
public class AnniversaryController {


    @Autowired
    AnniversaryService anniversaryService;

    /**
     * 查询用户的纪念日
     *
     * @param vipId 客户id
     * @return 查询该客户的纪念日
     */
    @GetMapping("/list")
    public ResponseEntity getAnniversaryList(@RequestParam Integer vipId) {

        log.info("请求查询用户的纪念日 ");
        List<AnniversaryDTO> anniversaryListByVipID = anniversaryService.getAnniversaryListByVipID(vipId);

        return ResponseEntity.ok(anniversaryListByVipID);
    }

    /**
     * 查询具体某个纪念日
     * @param anniversaryId 纪念日id
     * @return 纪念日信息
     */
    @GetMapping("/exactanniversary")
    public ResponseEntity getExactAnniversary(@RequestParam Integer anniversaryId) {

        Anniversary exactAnniversary = anniversaryService.getExactAnniversary(anniversaryId);

        return ResponseEntity.ok(exactAnniversary);
    }


    /**
     * 删除纪念日
     * @param anniversaryId 纪念日id
     * @return 删除结果
     */
    @GetMapping("/deleteinfo")
    public ResponseEntity deleteExactAnniversary(@RequestParam Integer anniversaryId) {

        Boolean b = anniversaryService.deleteExactAnniversary(anniversaryId);
        Tip tip = (b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }


    /**
     * 编辑纪念日信息
     * @param anniversaryDTO 纪念日信息dto
     * @return 操作结果
     */
    @PostMapping("/info")
    public ResponseEntity editExactAnniversary(@RequestBody AnniversaryDTO anniversaryDTO) {

        Boolean b = anniversaryService.editExactAnniversary(anniversaryDTO);
        Tip tip = (b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }


    /**
     * 客户关怀
     * @param customerCareDTO 客户关怀筛选信息
     * @return 返回客户关怀信息
     */
    @PostMapping("/customercare")
    public ResponseEntity getCustomerCare(@RequestBody CustomerCareDTO customerCareDTO) {

        //客户关怀
        Page<CustomerCareDTO> customerCarePage  = anniversaryService.getCustomerCarePage(customerCareDTO);


        return ResponseEntity.ok(customerCarePage);
    }
}
