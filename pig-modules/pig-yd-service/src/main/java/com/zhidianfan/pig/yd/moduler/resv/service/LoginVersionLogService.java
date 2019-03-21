package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.LoginVersionLog;
import com.zhidianfan.pig.yd.moduler.common.service.ILoginVersionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: huzp
 * @Date: 2018/12/17 10:18
 * @DESCRIPTION
 */
@Service
public class LoginVersionLogService {


    @Autowired
    ILoginVersionLogService iLoginVersionLogService;


    /**
     * 插入该账户的最近登录记录,最多十条
     * @param terminal 登录设备
     * @param account 登录账户
     * @param version 版本号
     */
    public Boolean insertVersionLog(String terminal, String account, String version) {

        //1.先判断登录记录是否超过十条
        int i = iLoginVersionLogService.selectCount(new EntityWrapper<LoginVersionLog>()
                .eq("terminal", terminal)
                .eq("account", account));

        LoginVersionLog loginVersionLog = new LoginVersionLog();
        //操作标志
        boolean flag ;

        if (i >= 10){
            //更新最早的一条数据
            loginVersionLog = iLoginVersionLogService.selectOne(new EntityWrapper<LoginVersionLog>()
                    .eq("terminal", terminal)
                    .eq("account", account)
                    .orderBy("created_at")
                    .last("limit 0,1"));

            loginVersionLog.setTerminal(terminal);
            loginVersionLog.setAccount(account);
            loginVersionLog.setVersion(version);
            loginVersionLog.setCreatedAt(new Date());

            flag = iLoginVersionLogService.update(loginVersionLog, new EntityWrapper<LoginVersionLog>()
                    .eq("id", loginVersionLog.getId()));


        }else {
            //直接插入
            loginVersionLog.setTerminal(terminal);
            loginVersionLog.setAccount(account);
            loginVersionLog.setVersion(version);
            loginVersionLog.setCreatedAt(new Date());
            flag= iLoginVersionLogService.insert(loginVersionLog);
        }

        return  flag;
    }

}
