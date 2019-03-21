package com.zhidianfan.pig.yd.moduler.push.ws;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.PushWsRegInfo;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.PushWsRegInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author sjl
 * 2019-03-04 10:13
 * Spring 容器启动时，将数据库中原先的状态全部修改为已下线 - 0
 */
@Component
@Slf4j
public class WSOffline implements InitializingBean {

    @Autowired
    private PushWsRegInfoMapper pushRegInfoDao;

    /**
     * 在 bean 创建完成后执行该操作
     */
    @Override
    public void afterPropertiesSet() throws Exception{
        // todo 全部删除， 执行 truncate 语句
        pushRegInfoDao.delete(new EntityWrapper<PushWsRegInfo>().where("1=1"));
    }
}
