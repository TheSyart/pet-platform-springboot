package com.pets.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRecord {

    public String id;

    private String orderId;

    private Integer operatorType;

    private String operatorId;

    private Integer operationContent;

    private String operatorTime;

}