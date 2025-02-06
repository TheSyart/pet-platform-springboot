package com.pets.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPetQueryDTO {

    private String petName;

    private String userName;

    private String petBreed;

    private Integer gender;

    private Integer status;

    private String begin;

    private String end;
}
