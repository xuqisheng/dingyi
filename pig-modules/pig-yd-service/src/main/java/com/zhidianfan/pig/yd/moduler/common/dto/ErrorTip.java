package com.zhidianfan.pig.yd.moduler.common.dto;

/**
 * 通用失败返回
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/17
 * @Modified By:
 */

public class ErrorTip implements Tip {

    private int code = 500;
    private String msg = "执行失败";

    public static final ErrorTip ERROR_TIP = new ErrorTip();

    public ErrorTip() {
    }



    public ErrorTip(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
