package com.pets.pojo.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PetService {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private String createDate;

    private String updateDate;

    private Integer shoppingTypeId;

    private Integer status;

    private String image;
}
