package com.zhidianfan.pig.push.controller.dto;


import lombok.Data;

@Data
public class DeviceTagAliasDTO {

    /**
     * 更新设备的别名属性；当别名为空串时，删除指定设备的别名；
     */
    private String alias;

    private String mobile;

    /**
     * tags：支持 add, remove 或者空字符串。当 tags 参数为空字符串的时候，表示清空所有的 tags；add/remove 下是增加或删除指定的 tag；
     * 一次 add/remove tag 的上限均为 100 个，且总长度均不能超过 1000 字节。
     * 可以多次调用 API 设置，一个设备（registrationID）能设置的 tag 上限为 1000 个，应用 tag 总数没有限制 。
     */
    private TagsDTO tags;


}
