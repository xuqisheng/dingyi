package com.zhidianfan.pig.yd.moduler.welcrm.dto;

import lombok.Data;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2019-05-16
 * @Modified By:
 */
@Data
public class AppraiseDTO {

    /**
     * 微生活商户ID
     */
    private Integer bid;

    /**
     * 推送总页数，数据超过10000条会分批推送剩余数据
     */
    private Integer totalPage;

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 每页数据条数
     */
    private Integer perPage;

    /**
     * 推送的数据远程文件地址
     */
    private String ossUrl;

    /**
     * 推送的数据内容
     */
    private String pushData;

}
