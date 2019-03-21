package com.zhidianfan.pig.yd.sms.dto;

/**
 * 乐信通短信
 * @Author 李凌峰
 * @Description
 * @Date: 2018/10/24 0024 下午 3:06
 * @Modified By:
 */
public class LxMsgContent {

    /**
     * sname : dlzhanglong1
     * spwd : dlzhanglong123
     * scorpid : 1
     * sprdid : 1012808
     * sdst : 18946682163
     * smsg : 李先生1，您好！...
     */

    private String sname;
    private String spwd;
    private String scorpid;
    private String sprdid;
    private String sdst;
    private String smsg;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSpwd() {
        return spwd;
    }

    public void setSpwd(String spwd) {
        this.spwd = spwd;
    }

    public String getScorpid() {
        return scorpid;
    }

    public void setScorpid(String scorpid) {
        this.scorpid = scorpid;
    }

    public String getSprdid() {
        return sprdid;
    }

    public void setSprdid(String sprdid) {
        this.sprdid = sprdid;
    }

    public String getSdst() {
        return sdst;
    }

    public void setSdst(String sdst) {
        this.sdst = sdst;
    }

    public String getSmsg() {
        return smsg;
    }

    public void setSmsg(String smsg) {
        this.smsg = smsg;
    }
}
