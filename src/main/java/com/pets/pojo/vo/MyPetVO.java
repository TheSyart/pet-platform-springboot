package com.pets.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPetVO {

    private String name;

    private String id;

    private String parent_id;

    private String pet_name;

    private Integer pet_sex;

    private Integer pet_breed;

    private String pet_weight;

    private String pet_birth;

    private Integer pet_coat;

    private Integer pet_details;

    private String image;

    private Integer status;

    private String createDate;

    private String modifyDate;
}
