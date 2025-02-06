package com.pets.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPetAndroidVO {

    private String id;

    private String parent_id;

    private String pet_name;

    private String pet_sex;

    private String pet_breed;

    private String pet_weight;

    private String pet_birth;

    private String pet_coat;

    private String pet_details;

    private String image;
}
