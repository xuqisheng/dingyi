package com.zhidianfan.pig.yd.moduler.push.dto;

import java.util.List;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/30
 * @Modified By:
 */

public class NodePush {

    private int code;
    private String message;
    private List<String> registrations;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<String> registrations) {
        this.registrations = registrations;
    }


}
