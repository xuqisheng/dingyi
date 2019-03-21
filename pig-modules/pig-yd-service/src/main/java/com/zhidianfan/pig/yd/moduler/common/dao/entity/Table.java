package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hzp
 * @since 2018-10-19
 */
@TableName("`table`")
public class Table extends Model<Table> {

    
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    @TableField("table_area_id")
    private Integer tableAreaId;
    @TableField("area_code")
    private String areaCode;
    @TableField("table_code")
    private String tableCode;
    @TableField("table_name")
    private String tableName;
    private String status;
    @TableField("max_people_num")
    private String maxPeopleNum;
    @TableField("table_des")
    private String tableDes;
    private String remark;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("table_url")
    private String tableUrl;
    /**
     * 是否做换衣间0是否 1是是
     */
    @TableField("is_locker_room")
    private Integer isLockerRoom;
    @TableField("table_type")
    private String tableType;
    @TableField("related_table")
    private String relatedTable;
    /**
     * 0-包厢 1-散台 2-卡座
     */
    @TableField("t_type")
    private Integer tType;
    /**
     * 面积: 宴会是大厅面积, 包厢是包厢面积, 散台, 雅座没有
     */
    @TableField("room_area")
    private Integer roomArea;
    /**
     * 层高: 0为未设置层高
     */
    @TableField("floor_height")
    private Integer floorHeight;
    /**
     * 大厅有无柱, 只有宴会需要
     */
    private Integer pillar;
    /**
     * 包厢有无厕所, 只有包厢有, 0-没有, 1-有
     */
    private Integer washroom;
    /**
     * 包厢有无沙发, 只有包厢有, 0-没有, 1-有
     */
    private Integer sofa;
    /**
     * 有无电视, 只有包厢有
     */
    private Integer television;
    @TableField("sort_id")
    private Integer sortId;
    @TableField("reserved_time")
    private Integer reservedTime;
    /**
     * 桌位容纳最小人数
     */
    @TableField("min_people_num")
    private String minPeopleNum;
    /**
     * 严重超时时间（分）
     */
    @TableField("timeout_time")
    private Integer timeoutTime;

    /**
     * 桌位类型id
     */
    @TableField("table_type_id")
    private Integer tableTypeId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getTableAreaId() {
        return tableAreaId;
    }

    public void setTableAreaId(Integer tableAreaId) {
        this.tableAreaId = tableAreaId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMaxPeopleNum() {
        return maxPeopleNum;
    }

    public void setMaxPeopleNum(String maxPeopleNum) {
        this.maxPeopleNum = maxPeopleNum;
    }

    public String getTableDes() {
        return tableDes;
    }

    public void setTableDes(String tableDes) {
        this.tableDes = tableDes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTableUrl() {
        return tableUrl;
    }

    public void setTableUrl(String tableUrl) {
        this.tableUrl = tableUrl;
    }

    public Integer getIsLockerRoom() {
        return isLockerRoom;
    }

    public void setIsLockerRoom(Integer isLockerRoom) {
        this.isLockerRoom = isLockerRoom;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getRelatedTable() {
        return relatedTable;
    }

    public void setRelatedTable(String relatedTable) {
        this.relatedTable = relatedTable;
    }

    public Integer gettType() {
        return tType;
    }

    public void settType(Integer tType) {
        this.tType = tType;
    }

    public Integer getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(Integer roomArea) {
        this.roomArea = roomArea;
    }

    public Integer getFloorHeight() {
        return floorHeight;
    }

    public void setFloorHeight(Integer floorHeight) {
        this.floorHeight = floorHeight;
    }

    public Integer getPillar() {
        return pillar;
    }

    public void setPillar(Integer pillar) {
        this.pillar = pillar;
    }

    public Integer getWashroom() {
        return washroom;
    }

    public void setWashroom(Integer washroom) {
        this.washroom = washroom;
    }

    public Integer getSofa() {
        return sofa;
    }

    public void setSofa(Integer sofa) {
        this.sofa = sofa;
    }

    public Integer getTelevision() {
        return television;
    }

    public void setTelevision(Integer television) {
        this.television = television;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getReservedTime() {
        return reservedTime;
    }

    public void setReservedTime(Integer reservedTime) {
        this.reservedTime = reservedTime;
    }

    public String getMinPeopleNum() {
        return minPeopleNum;
    }

    public void setMinPeopleNum(String minPeopleNum) {
        this.minPeopleNum = minPeopleNum;
    }

    public Integer getTimeoutTime() {
        return timeoutTime;
    }

    public void setTimeoutTime(Integer timeoutTime) {
        this.timeoutTime = timeoutTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Integer getTableTypeId() {
        return tableTypeId;
    }

    public void setTableTypeId(Integer tableTypeId) {
        this.tableTypeId = tableTypeId;
    }

    @Override
    public String toString() {
        return "Table{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", tableAreaId=" + tableAreaId +
        ", areaCode=" + areaCode +
        ", tableCode=" + tableCode +
        ", tableName=" + tableName +
        ", status=" + status +
        ", maxPeopleNum=" + maxPeopleNum +
        ", tableDes=" + tableDes +
        ", remark=" + remark +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", tableUrl=" + tableUrl +
        ", isLockerRoom=" + isLockerRoom +
        ", tableType=" + tableType +
        ", relatedTable=" + relatedTable +
        ", tType=" + tType +
        ", roomArea=" + roomArea +
        ", floorHeight=" + floorHeight +
        ", pillar=" + pillar +
        ", washroom=" + washroom +
        ", sofa=" + sofa +
        ", television=" + television +
        ", sortId=" + sortId +
        ", reservedTime=" + reservedTime +
        ", minPeopleNum=" + minPeopleNum +
        ", timeoutTime=" + timeoutTime +
        ", tableTypeId=" + tableTypeId +
        "}";
    }
}
