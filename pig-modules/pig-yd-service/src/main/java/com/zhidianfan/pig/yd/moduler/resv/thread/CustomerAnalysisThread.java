package com.zhidianfan.pig.yd.moduler.resv.thread;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessCustomerAnalysis;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.CustomerAnalysisMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 客户分类相关线程
 *
 * @author wangyz
 * @version v 0.1 2019-03-19 18:57 wangyz Exp $
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAnalysisThread {



    /**
     * mapper
     */
    private CustomerAnalysisMapper customerAnalysisMapper;




}
