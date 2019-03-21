package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Anniversary;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzp
 * @since 2019-01-21
 */
public interface IAnniversaryService extends IService<Anniversary> {

    List<Anniversary> getPastAnniversaryVip();

}
