package com.pets.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingVO {

    private String id;

    private String name;

    private Double price;

    private Integer stock;

    private String createDate;

    private String updateDate;

    private Integer shoppingTypeId;

    private Integer status;

}
