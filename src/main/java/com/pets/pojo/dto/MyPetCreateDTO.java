package com.pets.pojo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPetCreateDTO {
    //通过这个获得parent_id
    private String username;

    private String id;

    private String parent_id;

    @NotBlank(message = "宠物姓名不能为空")
    private String pet_name;

    @NotNull(message = "宠物性别不能为空")
    private Integer pet_sex;

    @NotNull(message = "宠物品种不能为空")
    private Integer pet_breed;

    @NotBlank(message = "宠物体重不能为空")
    private String pet_weight;

    @NotBlank(message = "宠物生日不能为空")
    private String pet_birth;

    @NotNull(message = "宠物毛色不能为空")
    private Integer pet_coat;

    @NotNull(message = "宠物备注不能为空")
    private Integer pet_details;

    @NotBlank(message = "宠物照片不能为空")
    private String image;
}
