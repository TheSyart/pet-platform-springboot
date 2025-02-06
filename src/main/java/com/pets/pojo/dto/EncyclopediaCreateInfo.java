package com.pets.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncyclopediaCreateInfo {

    @NotNull(message = "宠物类别不能为空")
    private Long parentId;

    @NotBlank(message = "宠物名不能为空")
    private String petName;

    @NotBlank(message = "图片路径不能为空")
    private String image;

    @NotBlank(message = "内容不能为空")
    private String content;

    @NotBlank(message = "宠物体重不能为空")
    private String petWeight;

    @NotBlank(message = "宠物身高不能为空")
    private String petHeight;

    @NotBlank(message = "宠物寿命不能为空")
    private String petLife;

    @NotBlank(message = "宠物发源地不能为空")
    private String petOrigin;

    @NotBlank(message = "宠物体型不能为空")
    private String petShape;

    @NotBlank(message = "宠物别名不能为空")
    private String anotherName;

    @Pattern(regexp = "\\d+(\\.\\d+)?(-\\d+(\\.\\d+)?)?", message = "宠物价格格式不正确，例如: 800 或 800-7000")
    private String petPrice;
}

