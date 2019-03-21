package com.zhidianfan.pig.user.controller;

import com.zhidianfan.pig.user.dao.entity.Agent;
import com.zhidianfan.pig.user.dao.entity.YdUser;
import com.zhidianfan.pig.user.dto.AgentDTO;
import com.zhidianfan.pig.user.dto.PageDTO;
import com.zhidianfan.pig.user.dto.Tip;
import com.zhidianfan.pig.user.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/2
 * @Modified By:
 */
@RestController
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    AgentService agentService;


    /**
     * 获取经销商列表
     * @param agentDTO
     * @return
     */
    @GetMapping("/get/page")
    public ResponseEntity getAgent(AgentDTO agentDTO){
        PageDTO pageDTO = agentService.getAgent(agentDTO);
        return ResponseEntity.ok(pageDTO);
    }

    /**
     * 修改经销商信息
     * @param agentDTO
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity updateAgent(@RequestBody AgentDTO agentDTO){
        Tip tip = agentService.updateAgent(agentDTO);
        return ResponseEntity.ok(tip);
    }

    /**
     * 添加经销商信息
     * @param agentDTO
     * @return
     */
    @PostMapping("/put")
    public ResponseEntity putAgent(@RequestBody AgentDTO agentDTO){
        Tip tip = agentService.putAgent(agentDTO);
        return ResponseEntity.ok(tip);
    }

    /**
     * 获取经销商信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseEntity getAgentById(@PathVariable Long id){
        Agent agent = agentService.getAgentById(id);
        return ResponseEntity.ok(agent);
    }

    /**
     * 登录
     * @param ydUser
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity loginCheck(YdUser ydUser){
        Tip tip = agentService.loginCheck(ydUser);
        return ResponseEntity.ok(tip);
    }

    /**
     * 合同编号校验
     * @param contractNo
     * @return
     */
    @GetMapping("/contract")
    public ResponseEntity contractNoCheck(String contractNo){
        Tip tip = agentService.contractNoCheck(contractNo);
        return ResponseEntity.ok(tip);
    }

}
