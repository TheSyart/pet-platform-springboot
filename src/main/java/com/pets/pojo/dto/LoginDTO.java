package com.pets.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "账户不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String phoneNumber;

    private String code;

    private String msg;

    private String jwt;

}
