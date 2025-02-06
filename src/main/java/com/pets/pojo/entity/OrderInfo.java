package com.pets.pojo.entity;

import lombok.Data;

import java.util.List;

@Data
public class OrderInfo {

    //商品编号
    private Long shopping_id;

    //订单编号
    private String order_id;

    //商品名称
    private String name;

    //商品单价
    private Double price;

    //商品数量
    private Integer quantity;

    //商品小计
    private Double subtotal;

    //商品图片
    private String image;

}
