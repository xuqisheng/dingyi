package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2019-03-25
 * @Modified By:
 */
@Data
public class ChangePasswordDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String validate;
}
