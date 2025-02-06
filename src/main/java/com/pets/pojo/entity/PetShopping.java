package com.pets.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetShopping implements Serializable {

    private Integer id;

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    private String createdate;

    private String updatedate;

    private Integer shoppingTypeId;

    private String image;

}

