package com.pets.pojo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpCreateInfo {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @Size(max = 12, message = "姓名超过最大长度12")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "生日不能为空")
    private String birth;

    @NotNull(message = "性别不能为空")
    private Short gender;

    @NotBlank(message = "头像不能为空")
    private String image;

    @NotNull(message = "职位不能为空")
    private Short job;
}
