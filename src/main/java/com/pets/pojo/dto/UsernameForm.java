package com.pets.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsernameForm {
    @NotNull(message = "用户名不能为空")
    private String username;
}
