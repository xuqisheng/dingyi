package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ljh
 * @since 2018-12-19
 */
@TableName("rating_config")
public class RatingConfig extends Model<RatingConfig> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 等级
     */
    private Integer grade;
    /**
     * 内容
     */
    private String content;
    /**
     * 标签
     */
    private String label;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "RatingConfig{" +
        "id=" + id +
        ", grade=" + grade +
        ", content=" + content +
        ", label=" + label +
        "}";
    }
}
