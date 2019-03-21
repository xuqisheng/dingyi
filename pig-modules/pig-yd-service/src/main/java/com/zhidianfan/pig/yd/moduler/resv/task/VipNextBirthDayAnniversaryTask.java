package com.zhidianfan.pig.yd.moduler.resv.task;

import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.resv.service.VipNextBirthDayAnniversaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huzp
 * @date 2019/1/21  15:02
 * @description 客户下次生日下次纪念日计算
 */
@RestController
@RequestMapping("/vipnexttime")
@Slf4j
public class VipNextBirthDayAnniversaryTask {

    @Autowired
    VipNextBirthDayAnniversaryService vipNextBirthDayAnniversaryService;

    @PostMapping("/nextbirthday")
    public ResponseEntity nextBirthday() {

        vipNextBirthDayAnniversaryService.calNextBirthday();
        vipNextBirthDayAnniversaryService.calNextAnniversary();

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }

}
