package com.zhidianfan.pig.yd.moduler.sms.dto.meeting.order;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvMeetingOrder;

public class ResvMeetingOrderDto extends ResvMeetingOrder {

    /**
     * 担保人姓名
     */
    private String dbrName;

    public String getDbrName() {
        return dbrName;
    }

    public void setDbrName(String dbrName) {
        this.dbrName = dbrName;
    }
}
