package com.zhidianfan.pig.yd.mq;

import com.zhidianfan.pig.yd.moduler.common.constant.QueueName;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.NowChangeInfo;
import com.zhidianfan.pig.yd.moduler.common.service.INowChangeInfoService;
import com.zhidianfan.pig.yd.moduler.resv.dto.CustomerValueChangeFieldDTO;
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

    @RabbitHandler
    @RabbitListener(queues = QueueName.CUSTOMER_VALUE_CHANGE_FIELD)
    public void updateField(CustomerValueChangeFieldDTO customerValueChangeFieldDTO) {
        checkParam(customerValueChangeFieldDTO);

        NowChangeInfo info = new NowChangeInfo();
        info.setVipId(customerValueChangeFieldDTO.getVipId());
        info.setValue(customerValueChangeFieldDTO.getValue());
        info.setType(customerValueChangeFieldDTO.getType());
        info.setChangeTime(LocalDateTime.now());
        info.setRemark(StringUtils.EMPTY);
        info.setCreateTime(LocalDateTime.now());
        info.setUpdateTime(LocalDateTime.now());

        nowChangeInfoService.insert(info);
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
