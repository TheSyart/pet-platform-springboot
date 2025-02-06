package com.pets.pojo.entity;

import lombok.Data;

@Data
public class FeedingSkill {

    private Long id;

    private String title;

    private String content;

    private String image;

    private String createDate;

    private String updateDate;

    private Integer status;
}
