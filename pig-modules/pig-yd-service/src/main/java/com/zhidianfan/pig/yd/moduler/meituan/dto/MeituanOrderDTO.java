package com.zhidianfan.pig.yd.moduler.meituan.dto;

public class MeituanOrderDTO {
    /**
     * orderSerializedId : 1000548541
     * status : 10
     * bookingTime : 1498206600
     * number : 11
     * name : 李
     * gender : 20
     * phone : 18212347718
     * requirements : {"tableType":0,"tableTypeName":"大厅","remark":"有小孩"}
     * createTime : 1498204600
     */

    private String orderSerializedId;
    private int status;
    private int bookingTime;
    private int number;
    private String name;
    private int gender;
    private String phone;
    private RequirementsBean requirements;
    private int createTime;

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

    public int getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(int bookingTime) {
        this.bookingTime = bookingTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RequirementsBean getRequirements() {
        return requirements;
    }

    public void setRequirements(RequirementsBean requirements) {
        this.requirements = requirements;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public static class RequirementsBean {
        /**
         * tableType : 0
         * tableTypeName : 大厅
         * remark : 有小孩
         */

        private int tableType;
        private String tableTypeName;
        private String remark;

        public int getTableType() {
            return tableType;
        }

        public void setTableType(int tableType) {
            this.tableType = tableType;
        }

        public String getTableTypeName() {
            return tableTypeName;
        }

        public void setTableTypeName(String tableTypeName) {
            this.tableTypeName = tableTypeName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
