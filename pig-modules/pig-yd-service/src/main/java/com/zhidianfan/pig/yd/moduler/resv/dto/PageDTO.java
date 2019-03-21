package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/2
 * @Modified By:
 */
@Data
public class PageDTO {

    private long total;

    private int page;

    private long records;

    private List rows;
}
