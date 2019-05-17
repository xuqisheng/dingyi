package com.zhidianfan.pig.yd.moduler.welcrm.dto;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2019-05-16
 * @Modified By:
 */
public class PushDataDTO {


    /**
     * user_phone : 18811124780
     * user_num : 1202248
     * dish_id : 113
     * dish_score : 2
     * dish_options : 有点辣
     * server_id : 5274
     * server_score : 2
     * server_options : 态度好,上菜快
     * bid : 1991243684
     * sid : 3653866669
     * table_id : 101
     * tctotal_fee : 2000
     * tcFee : 2000
     * tc_id : 1609303975514666
     * tc_time : 2018-08-20 15:57:48
     * cm_time : 2018-08-20 16:29:37
     */

    private String user_phone;
    private int dish_id;
    private int dish_score;
    private String dish_options;
    private String server_id;
    private String server_score;
    private String server_options;
    private String bid;
    private String sid;
    private String table_id;
    private String tctotal_fee;
    private String tcFee;
    private String tc_id;
    private String tc_time;
    private String cm_time;

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public int getDish_score() {
        return dish_score;
    }

    public void setDish_score(int dish_score) {
        this.dish_score = dish_score;
    }

    public String getDish_options() {
        return dish_options;
    }

    public void setDish_options(String dish_options) {
        this.dish_options = dish_options;
    }

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    public String getServer_score() {
        return server_score;
    }

    public void setServer_score(String server_score) {
        this.server_score = server_score;
    }

    public String getServer_options() {
        return server_options;
    }

    public void setServer_options(String server_options) {
        this.server_options = server_options;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getTctotal_fee() {
        return tctotal_fee;
    }

    public void setTctotal_fee(String tctotal_fee) {
        this.tctotal_fee = tctotal_fee;
    }

    public String getTcFee() {
        return tcFee;
    }

    public void setTcFee(String tcFee) {
        this.tcFee = tcFee;
    }

    public String getTc_id() {
        return tc_id;
    }

    public void setTc_id(String tc_id) {
        this.tc_id = tc_id;
    }

    public String getTc_time() {
        return tc_time;
    }

    public void setTc_time(String tc_time) {
        this.tc_time = tc_time;
    }

    public String getCm_time() {
        return cm_time;
    }

    public void setCm_time(String cm_time) {
        this.cm_time = cm_time;
    }
}
