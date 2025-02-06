package com.pets.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetServiceV0 {

    private Integer id;

    private String name;

    private String description;

    private Double price;

    private String createdate;

    private String updatedate;

    private Integer shoppingTypeId;

    private Integer status;

}
