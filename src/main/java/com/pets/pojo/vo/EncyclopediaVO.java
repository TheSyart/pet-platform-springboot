package com.pets.pojo.vo;

import lombok.Data;

@Data
public class EncyclopediaVO {

    private Long id;

    private Long parentId;

    private String petName;

    private String petOrigin;

    private String petLife;

    private String anotherName;

    private String petPrice;

    private Integer status;

    private String createDate;

    private String updateDate;
}
