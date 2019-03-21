package com.zhidianfan.pig.push.controller.dto;


import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


import java.util.Date;
import java.util.Set;


@Data
 public class DeviceDTO {


    /**
     * 设备ID
     */
    @NotBlank(message = "设备id不能为空")
    private String registrationId;
    /**
     * 别名（最好唯一）
     */
    private String alias;
    /**
     * 标签（, 分隔）
     */
    private Set<String> tag;

    /**
     * 状态 1启用0停用
     */
    private Integer isEnable;


   /**
    * 设备类型 android   ios
    */
   private String deviceType;


    /**
     * 手机号码
     */
   private String mobile;

}
