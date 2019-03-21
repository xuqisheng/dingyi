package com.zhidianfan.pig.common.constant;


/**
 * 通用成功返回
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/17
 * @Modified By:
 */

public class SuccessTip implements Tip {
    private int code = 200;
    private String msg = "执行成功";

    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public static final SuccessTip SUCCESS_TIP = new SuccessTip();

    public SuccessTip() {
    }

    public SuccessTip(int code, String msg) {
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
