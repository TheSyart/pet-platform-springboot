package com.pets.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateInfo {

    @NotBlank(message = "手机号不能为空")
    private String phone;

    @NotNull(message = "性别不能为空")
    private Integer gender;

    @NotBlank(message = "验证码不能为空")
    private String code;

    @NotBlank(message = "生日不能为空")
    private String birth;

}
