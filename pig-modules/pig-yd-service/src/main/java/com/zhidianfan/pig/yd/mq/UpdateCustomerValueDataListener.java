package com.zhidianfan.pig.yd.mq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhidianfan.pig.yd.moduler.common.constant.QueueName;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Anniversary;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.NowChangeInfo;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.service.IAnniversaryService;
import com.zhidianfan.pig.yd.moduler.common.service.INowChangeInfoService;
import com.zhidianfan.pig.yd.moduler.common.service.IVipService;
import com.zhidianfan.pig.yd.moduler.resv.dto.CustomerValueChangeFieldDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.VipConsumeActionTotalService;
import com.zhidianfan.pig.yd.moduler.resv.service.VipService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 监听 MQ 中的队列，实时插入到表中
 * @author sjl
 * 2019-05-29 09:27
 */
@Slf4j
@Component
public class UpdateCustomerValueDataListener {

    @Autowired
    private INowChangeInfoService nowChangeInfoService;

    @Autowired
    private VipConsumeActionTotalService vipConsumeActionTotalService;

    @Autowired
    private VipService vipService;

    @Autowired
    private IVipService iVipService;


    @RabbitHandler
    @RabbitListener(queues = QueueName.CUSTOMER_VALUE_QUEUE)
    public void updateField(String content) {
        try {
            consumerMessage(content);
        } catch (Exception e) {
            log.error("消费 MQ 消息发生异常, 消息内容为:[{}]",content, e);
        }
    }

    private void consumerMessage(String content) {
        log.debug("接收到 MQ 的消息，开始处理:[{}]", content);
        CustomerValueChangeFieldDTO customerValueChangeFieldDTO;
        try {
            customerValueChangeFieldDTO = JSON.parseObject(content, CustomerValueChangeFieldDTO.class);
        } catch (Exception e) {
            log.error("转换到 Object 异常：{}", content);
            return;
        }
        checkParam(customerValueChangeFieldDTO);
        String type = customerValueChangeFieldDTO.getType();
        Integer vipId = customerValueChangeFieldDTO.getVipId();
        String value = customerValueChangeFieldDTO.getValue();
        if (CustomerValueChangeFieldDTO.FIRST_CUSTOMER_TIME.equals(type)) {
            // 首次消费时间
            vipConsumeActionTotalService.updateFirstConsumeTime(vipId, value);
        } else if (CustomerValueChangeFieldDTO.CANCEL_ORDER_TABLE.equals(type)) {
            // 撤单桌数
            vipConsumeActionTotalService.updateCancelTableNo(vipId, value);
        } else if (CustomerValueChangeFieldDTO.PROFILE.equals(type)) {
            NowChangeInfo info = new NowChangeInfo();
            info.setVipId(customerValueChangeFieldDTO.getVipId());
            Vip vip = iVipService.selectById(vipId);
            int profileScore = vipService.getProfile(vip);
            info.setValue(profileScore);
            info.setType(customerValueChangeFieldDTO.getType());
            info.setChangeTime(LocalDateTime.now());
            info.setRemark(StringUtils.EMPTY);
            info.setCreateTime(LocalDateTime.now());
            info.setUpdateTime(LocalDateTime.now());

            nowChangeInfoService.insertOrUpdate(info);
        } else {
            log.error("错误的类型,{}", customerValueChangeFieldDTO);
        }
    }


    /**
     * 参数校验
     * @param customerValueChangeFieldDTO dto 内的参数校验
     */
    private void checkParam(CustomerValueChangeFieldDTO customerValueChangeFieldDTO) {
        Integer vipId = customerValueChangeFieldDTO.getVipId();
        String type = customerValueChangeFieldDTO.getType();
        String value = customerValueChangeFieldDTO.getValue();
        if (vipId == null) {
            throw new RuntimeException("Vip 为空");
        }
        if (StringUtils.isBlank(type)) {
            throw new RuntimeException("type 为空");
        }
        if (StringUtils.isBlank(value)) {
            throw new RuntimeException("value 为空");
        }

    }

}
