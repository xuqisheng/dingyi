package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.yd.moduler.resv.service.CorrectBrthdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/correct")
public class CorrectBrthdayController {

    @Autowired
    CorrectBrthdayService correctBrthdayService;


    @PostMapping("/vipbirth")
    public ResponseEntity CorrectBrthday(){

        correctBrthdayService.CorrectVipBirth();
        return ResponseEntity.ok("");
    }


    @PostMapping("/specificotelhvipbirth")
    public ResponseEntity CorrectSpecificotelHVipBirth(){

        correctBrthdayService.CorrectSpecificotelHVipBirth();
        return ResponseEntity.ok("");
    }

}
