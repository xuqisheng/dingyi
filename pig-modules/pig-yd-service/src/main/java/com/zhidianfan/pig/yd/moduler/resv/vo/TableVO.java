package com.zhidianfan.pig.yd.moduler.resv.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/13
 * @Modified By:
 */
@Data
public class TableVO {
    /**
     * 桌位名称
     */
    private String tableName;
    /**
     * 桌位确认状态
     */
    private String confirm;
    /**
     * 桌位确认文字说明
     */
    private String confirmText;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
        if(StringUtils.equals(this.confirm,"1")){
            this.confirmText = "已确认";
        }else{
            this.confirmText = "未确认";
        }
    }

    public String getConfirmText() {
        return confirmText;
    }

    public void setConfirmText(String confirmText) {
        this.confirmText = confirmText;
    }
}
