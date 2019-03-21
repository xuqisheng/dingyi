package com.zhidianfan.pig.yd.moduler.resv.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessCustomerAnalysis;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipValue;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.CustomerAnalysisMapper;
import com.zhidianfan.pig.yd.moduler.resv.thread.CustomerAnalysisThread;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 客户分析接口
 *
 * @author wangyz
 * @version v 0.1 2019-03-19 15:06 wangyz Exp $
 */
@Service
public class CustomerAnalysisService {

    @Autowired
    private CustomerAnalysisMapper customerAnalysisMapper;

    private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 6, 10000, TimeUnit.MILLISECONDS, queue);

    void customerAnalysis(LocalDateTime begin, LocalDateTime end) {
        //清理旧数据
        cleanCustomerAnalysis(begin);
        //时间年月
        final String date = begin.getYear() + "-" + begin.getMonthValue();
        //获取所有酒店id
        final List<Integer> businessIds = customerAnalysisMapper.queryBusinessIds();
        //先插入所有酒店当月的客户分析结果
        for (Integer businessId : businessIds) {
            BusinessCustomerAnalysis customerAnalysis = new BusinessCustomerAnalysis();
            customerAnalysis.setDate(date);
            customerAnalysis.setBusinessId(businessId);
            customerAnalysisMapper.insert(customerAnalysis);
        }

        threadPool.execute(() -> new NewVipRunnable(date, begin, end, businessIds).run());


    }


    /**
     * 清理脏数据
     */
    private void cleanCustomerAnalysis(@NotNull LocalDateTime localDateTime) {
        String date = localDateTime.getYear() + "-" + localDateTime.getMonthValue();
        customerAnalysisMapper.cleanCustomerAnalysis(date);
    }
    

    /**
     * 新增用户
     */
    @Data
    @AllArgsConstructor
    public class NewVipRunnable implements Runnable {

        /**
         * 日期   yyyy-MM
         */
        private String date;

        /**
         * 开始时间
         */
        private LocalDateTime begin;

        /**
         * 结束时间
         */
        private LocalDateTime end;

        /**
         * 酒店id
         */
        private List<Integer> businessIds;

        @Override
        public void run() {
            for (Integer businessId : businessIds) {
                List<Integer> ids = customerAnalysisMapper.getNewVip(businessId, begin, end);

                BusinessCustomerAnalysis customerAnalysis = new BusinessCustomerAnalysis();
                customerAnalysis.setDate(date);
                customerAnalysis.setBusinessId(businessId);
                customerAnalysis.setNewVipCount(ids.size());
                customerAnalysisMapper.updateByBusinessIdAndDate(customerAnalysis);
            }
        }
    }
}