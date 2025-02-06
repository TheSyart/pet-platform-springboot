package com.pets.pojo.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

@Data
public class PetOrder implements Serializable {
    private Integer id;
    private String orderId;
    private Long customerId;
    private Double totalAmount;
    private String paymentMethod;
    private Date addressId;
    private String note;
    private Integer orderstatus;
    private Integer status;
    private Date createdate;
    private Date updatedate;

}

