package com.zhidianfan.pig.yd.moduler.order.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mikrotik
 */
@Getter
@Setter
@ToString
public class LoginBO {
    private boolean success;
    private String session;
    private Long ts;
}
