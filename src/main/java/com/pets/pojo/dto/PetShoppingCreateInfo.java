package com.pets.pojo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetShoppingCreateInfo {
    @NotBlank(message = "商品名称不能为空")
    private String name;

    @Size(max = 255, message = "商品简介不能超过255个字符")
    private String description;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格必须大于0")
    private Double price;

    @NotNull(message = "库存不能为空")
    private Integer stock;

    @NotNull(message = "商品类型ID不能为空")
    private Integer shoppingTypeId;

    @NotBlank(message = "图片路径不能为空")
    private String image;
}
