package com.pets.pojo.dto;

import lombok.Data;

@Data
public class FeedingSkillQueryDTO {

    private Integer page;

    private Integer pageSize;

    private String title;

    private Integer status;

    private String begin;

    private String end;

}
