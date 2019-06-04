package com.zhidianfan.pig.yd.mq;

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
    private IVipService vipMapper;

    @Autowired
    private IAnniversaryService anniversaryMapper;

    @RabbitHandler
    @RabbitListener(queues = QueueName.CUSTOMER_VALUE_QUEUE)
    public void updateField(CustomerValueChangeFieldDTO customerValueChangeFieldDTO) {
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
            String profileScore = getProfile(vipId);
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
     * 计算客户资料完整度
     * @param vipId vip id
     * @return
     */
    private String getProfile(Integer vipId) {
        log.info("开始计算客户资料完整度:[{}]", vipId);
        if (vipId == null) {
            return StringUtils.EMPTY;
        }

        // 查询 vip 表
        Vip vip = vipMapper.selectById(vipId);
        if (vip == null) {
            log.error("vip 信息不存在,vipId:[{}]", vipId);
            return StringUtils.EMPTY;
        }

        // -1 查询不到，不作处理
        // 查询纪念日表
        Wrapper<Anniversary> wrapper = new EntityWrapper<>();
        wrapper.eq("vip_id", vipId);
        int profileCount = anniversaryMapper.selectCount(wrapper);

        // -1 查询不到不作处理
        // 计算资料完整度
        String profileScore = getProfileScore(vip, profileCount);
        log.info("客户资料完整度，[{}],[{}]", vipId, profileCount);
        return profileScore;

        // 转换成 String
        // 返回结果
    }

    /**
     * 资料完整度
     * @param vip vip 信息
     * @param profileCount 纪念日
     * @return 85% 字样
     */
    private String getProfileScore(Vip vip, int profileCount) {
        int score = 0;
        String vipName = vip.getVipName();
        if (StringUtils.isNotBlank(vipName)) {
            score += 10;
        }

        String vipPhone = vip.getVipPhone();
        if (StringUtils.isNotBlank(vipPhone)) {
            score += 10;
        }

        String hobby = vip.getHobby();
        if (StringUtils.isNotBlank(hobby)) {
            score += 15;
        }

        String detest = vip.getDetest();
        if (StringUtils.isNotBlank(detest)) {
            score += 15;
        }

        String tag = vip.getTag();
        if (StringUtils.isNotBlank(tag)) {
            score += 10;
        }

        String vipCompany = vip.getVipCompany();
        if (StringUtils.isNotBlank(vipCompany)) {
            score += 10;
        }

        String vipBirthday = vip.getVipBirthday();
        String vipBirthdayNl = vip.getVipBirthdayNl();
        if (StringUtils.isNotBlank(vipBirthday)) {
            score += 15;
        } else if (StringUtils.isNotBlank(vipBirthdayNl)) {
            score += 15;
        }

        if (profileCount >= 0) {
            score += 15;
        }

        return score + "%";
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
