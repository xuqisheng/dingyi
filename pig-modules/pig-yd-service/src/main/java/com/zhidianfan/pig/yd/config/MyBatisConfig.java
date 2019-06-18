package com.zhidianfan.pig.yd.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/15
 * @Modified By:
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.zhidianfan.pig.yd.**.mapper")
public class MyBatisConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

//    @Bean
//    @Profile({"dev", "test"})// 设置 dev test 环境开启
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        performanceInterceptor.setFormat(true);//SQL是否格式化，默认false
//        return performanceInterceptor;
//    }
}
