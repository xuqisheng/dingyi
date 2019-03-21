package com.zhidianfan.pig.yd.moduler.dbo.sms;

import com.zhidianfan.pig.yd.moduler.dbo.BaseDO;

/**
 * 验证短信
 * Created by ck on 2018/6/8.
 */
public class BindcodeDO extends BaseDO {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 手机号
     */
    private String phonenum;

    /**
     * 验证码
     */
    private String code;

    /**
     * 默认为 1-可使用,0-不可用
     */
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
