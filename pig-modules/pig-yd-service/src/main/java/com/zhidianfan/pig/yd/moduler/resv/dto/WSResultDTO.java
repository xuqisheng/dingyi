package com.zhidianfan.pig.yd.moduler.resv.dto;

import java.util.List;

public class WSResultDTO {

    /**
     * code : 200
     * msg : 成功
     * body : [{"deviceType":"1","sessionId":"6:0:2"},{"deviceType":"1","sessionId":"6:0:0"},{"deviceType":"1","sessionId":"6:0:1"},{"deviceType":"1","sessionId":"6:0:3"}]
     */
    private int code;
    private String msg;
    private List<BodyBean> body;

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

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * deviceType : 1
         * sessionId : 6:0:2
         */

        private String deviceType;
        private String openid;

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }
    }
}
