package com.zhidianfan.pig.yd.moduler.common.dto;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/17
 * @Modified By:
 */

public class ResultTip<T> implements Tip {

    private int code = 200;
    private String msg = "执行成功";
    private T result;

    public ResultTip() {
    }

    public ResultTip(T result) {
        this.result = result;
    }

    public ResultTip(int code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
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
