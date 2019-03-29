package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Anniversary;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.dto.AnniversaryDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.AnniversaryService;
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
        List<Anniversary> anniversaryListByVipID = anniversaryService.getAnniversaryListByVipID(vipId);

        return ResponseEntity.ok(anniversaryListByVipID);
    }


    @GetMapping("/exactanniversary")
    public ResponseEntity getExactAnniversary(@RequestParam Integer anniversaryId) {

        Anniversary exactAnniversary = anniversaryService.getExactAnniversary(anniversaryId);

        return ResponseEntity.ok(exactAnniversary);
    }


    @PostMapping("/deleteinfo")
    public ResponseEntity deleteExactAnniversary(@Valid Integer anniversaryId) {

        Boolean b = anniversaryService.deleteExactAnniversary(anniversaryId);
        Tip tip = (b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }


    @PostMapping("/info")
    public ResponseEntity editExactAnniversary(@RequestBody AnniversaryDTO anniversaryDTO) {

        Boolean b = anniversaryService.editExactAnniversary(anniversaryDTO);
        Tip tip = (b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }

}
