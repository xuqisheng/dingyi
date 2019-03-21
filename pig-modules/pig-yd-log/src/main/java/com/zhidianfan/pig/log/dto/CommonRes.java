package com.zhidianfan.pig.log.dto;


/**
 * 通用返回
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/10/26
 * @Modified By:
 */
public class CommonRes {

    public static final CommonRes SUCCESS = new CommonRes(200, "执行成功");
    public static final CommonRes SUCCESS1 = new CommonRes(201, "异步调用成功，等待执行结果");
    public static final CommonRes ERROR = new CommonRes(500, "执行失败");

    private int code;
    private String msg;

    public CommonRes() {
    }

    public CommonRes(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommonRes{");
        sb.append("code=").append(code);
        sb.append(", msg='").append(msg).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
