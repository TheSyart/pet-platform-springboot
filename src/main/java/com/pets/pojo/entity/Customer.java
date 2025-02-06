package com.pets.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private String id;

    private String username;

    private String name;

    private String birth;

    private Integer status;

    private String createdate;

    private String modifydate;

    private String phone;

    private String password;

    private Integer gender;

    private String code;

    private String image;

}
