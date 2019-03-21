package com.zhidianfan.pig.yd.moduler.meituan.dto;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/9/29
 * @Modified By:
 */
public class MeituanOrderUpdateDTO {
    /**
     * orderSerializedId : 1000548541
     * status : 20
     * detail : {"reason":"临时有变"}
     */

    private String orderSerializedId;
    private int status;
    private DetailBean detail;

    public String getOrderSerializedId() {
        return orderSerializedId;
    }

    public void setOrderSerializedId(String orderSerializedId) {
        this.orderSerializedId = orderSerializedId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public static class DetailBean {
        /**
         * reason : 临时有变
         */

        private String reason;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
