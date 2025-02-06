package com.pets.pojo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetServiceCreateInfo {

    @NotBlank(message = "服务名称不能为空")
    private String name;

    @Size(max = 255, message = "服务简介不能超过255个字符")
    private String description;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格必须大于0")
    private Double price;

    @NotNull(message = "服务类型ID不能为空")
    private Integer shoppingTypeId;

    @NotBlank(message = "图片路径不能为空")
    private String image;
}
