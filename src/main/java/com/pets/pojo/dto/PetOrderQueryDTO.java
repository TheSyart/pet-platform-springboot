package com.pets.pojo.dto;

import lombok.Data;

@Data
public class PetOrderQueryDTO {

    //web分页使用
    private Integer page;

    private Integer pageSize;

    private String order_id;

    private Integer status;

    private String begin;

    private String end;

    private String name;

    private Integer pickMethod;

    private Integer order_type;

    private Integer orderStatus;
}
