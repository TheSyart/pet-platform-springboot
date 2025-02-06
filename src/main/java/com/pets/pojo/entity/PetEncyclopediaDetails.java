package com.pets.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetEncyclopediaDetails {

    private String id;

    private String parentId;

    private String petName;

    private String image;

    private String content;

    private String petWeight;

    private String petHeight;

    private String petLife;

    private String petOrigin;

    private String petShape;

    private String anotherName;

    private String petPrice;

    private Integer status;
}