package com.pets.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPet {

    private String id;

    private String parent_id;

    private String pet_name;

    private Integer pet_sex;

    private String pet_breed;

    private String pet_weight;

    private String pet_birth;

    private String pet_coat;

    private Integer pet_details;

    private String createDate;

    private String modifyDate;

    private String image_path;

    private Integer status;
}
