package com.zhidianfan.pig.yd.moduler.manage.bo;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomValueStatistics;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @User: ljh
 * @Date: 2019-01-24
 * @Time: 10:11
 */
@Data
public class CustomValueStatisticsBo extends Page{

    private Date lastStatisticsAt;


}
