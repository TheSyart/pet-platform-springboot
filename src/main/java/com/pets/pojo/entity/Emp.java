package com.pets.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp {

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String name;

    private String birth;

    private Integer status;

    private String entrydate;

    private String modifydate;

    private Short gender;

    private String image;

    private Short job;
}
