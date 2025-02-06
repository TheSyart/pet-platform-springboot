package com.pets.pojo.dto;

import lombok.Data;

@Data
public class OrderPartQueryDTO {

    private Integer num;

    private String username;

    private Integer order_type;

    private Integer orderPart;

}
