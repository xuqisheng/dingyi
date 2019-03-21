package com.zhidianfan.pig.yd.moduler.manage.controller;


import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Agent;
import com.zhidianfan.pig.yd.moduler.manage.dto.AgentDTO;
import com.zhidianfan.pig.yd.moduler.manage.dto.PageDTO;
import com.zhidianfan.pig.yd.moduler.manage.service.AgentManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/2
 * @Modified By:
 */
@RestController
@RequestMapping("/agent")
public class AgentManageController {

    @Autowired
    AgentManageService agentManageService;


    /**
     * 获取经销商列表
     * @param agentDTO
     * @return
     */
    @GetMapping("/get/page")
    public ResponseEntity getAgent(AgentDTO agentDTO){
        PageDTO pageDTO = agentManageService.getAgent(agentDTO);
        return ResponseEntity.ok(pageDTO);
    }

    /**
     * 未审核数量
     * @return
     */
    @GetMapping("/get/count")
    public ResponseEntity getAgentCount(AgentDTO agentDTO){
        Map<String, Integer> result = agentManageService.getAgentCount(agentDTO);
        return ResponseEntity.ok(result);
    }

    /**
     * 修改经销商信息
     * @param agentDTO
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity updateAgent(@RequestBody AgentDTO agentDTO){
        Tip tip = agentManageService.updateAgent(agentDTO);
        return ResponseEntity.ok(tip);
    }

    /**
     * 添加经销商信息
     * @param agentDTO
     * @return
     */
    @PostMapping("/put")
    public ResponseEntity putAgent(@RequestBody AgentDTO agentDTO){
        Tip tip = agentManageService.putAgent(agentDTO);
        return ResponseEntity.ok(tip);
    }

    /**
     * 获取经销商信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseEntity getAgentById(@PathVariable Long id){
        Agent agent = agentManageService.getAgentById(id);
        return ResponseEntity.ok(agent);
    }

//    /**
//     * 登录
//     * @param ydUser
//     * @return
//     */
//    @GetMapping("/login")
//    public ResponseEntity loginCheck(YdUser ydUser){
//        Tip tip = agentService.loginCheck(ydUser);
//        return ResponseEntity.ok(tip);
//    }

    /**
     * 合同编号校验
     * @param contractNo
     * @return
     */
    @GetMapping("/contract")
    public ResponseEntity contractNoCheck(String contractNo){
        Tip tip = agentManageService.contractNoCheck(contractNo);
        return ResponseEntity.ok(tip);
    }

    /**
     * 登录
     * @param
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity loginCheck(String username,String password,String clientId){
        Tip tip = agentManageService.loginCheck(username,password,clientId);
        return ResponseEntity.ok(tip);
    }

}
