package com.zhidianfan.pig.yd.moduler.meituan.dto;

import lombok.Data;

/**
 * @Author qqx
 * @Description callback回调参数
 * @Date Create in 2018/9/10
 * @Modified By:
 */

@Data
public class CallBackDTO {


    /**
     * appAuthToken : tokenvalue
     * businessId : 1
     * ePoiId : 10000
     * timestamp : 1489563617692
     * poiId : 1234
     * poiName : 湘北人家
     */

    private String appAuthToken;
    private String businessId;
    private String ePoiId;
    private String timestamp;
    private String poiId;
    private String poiName;

    public String getAppAuthToken() {
        return appAuthToken;
    }

    public void setAppAuthToken(String appAuthToken) {
        this.appAuthToken = appAuthToken;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getEPoiId() {
        return ePoiId;
    }

    public void setEPoiId(String ePoiId) {
        this.ePoiId = ePoiId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }
}
