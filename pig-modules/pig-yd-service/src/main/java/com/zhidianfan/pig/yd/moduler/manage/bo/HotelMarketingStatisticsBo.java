package com.zhidianfan.pig.yd.moduler.manage.bo;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.HotelMarketingStatistics;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @User: ljh
 * @Date: 2019-01-24
 * @Time: 14:17
 */
@Data
public class HotelMarketingStatisticsBo extends Page<HotelMarketingStatistics> {

    private Date lastStatisticsAt;
}
