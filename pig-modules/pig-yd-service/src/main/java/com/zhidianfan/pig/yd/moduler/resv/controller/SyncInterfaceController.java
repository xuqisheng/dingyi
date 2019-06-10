package com.zhidianfan.pig.yd.moduler.resv.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSyncAccount;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SyncInterface;
import com.zhidianfan.pig.yd.moduler.common.service.ISyncInterfaceService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author huzp
 * @since 2019-06-10
 */
@Controller
@RequestMapping("/syncinterface")
public class SyncInterfaceController {


    @Autowired
    ISyncInterfaceService iSyncInterfaceService;




    @GetMapping(value = "/all")
    public ResponseEntity getSyncInterface() {

        List<SyncInterface> syncInterfaces = iSyncInterfaceService.selectList(new EntityWrapper<SyncInterface>()
                .where("1 = 1"));

        return ResponseEntity.ok(syncInterfaces);

    }
}

