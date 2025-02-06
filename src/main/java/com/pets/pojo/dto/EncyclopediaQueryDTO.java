package com.pets.pojo.dto;

import lombok.Data;

@Data
public class EncyclopediaQueryDTO {

    private Integer page;

    private Integer pageSize;

    private Integer petSpecies;

    private String petName;

    private Integer status;

    private String begin;

    private String end;

}
