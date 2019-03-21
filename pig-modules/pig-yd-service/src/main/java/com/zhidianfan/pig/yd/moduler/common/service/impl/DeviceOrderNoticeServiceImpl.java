package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.DeviceOrderNotice;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.DeviceOrderNoticeMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IDeviceOrderNoticeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通知表:
  手机客户端预定，换桌，需预定台审核的预定内容的通知表。 服务实现类
 * </p>
 *
 * @author qqx
 * @since 2018-11-13
 */
@Service
public class DeviceOrderNoticeServiceImpl extends ServiceImpl<DeviceOrderNoticeMapper, DeviceOrderNotice> implements IDeviceOrderNoticeService {

}
