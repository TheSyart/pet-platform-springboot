package com.pets.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String name;
    private Short age;
    private Short gender;
    private String phone;


}
