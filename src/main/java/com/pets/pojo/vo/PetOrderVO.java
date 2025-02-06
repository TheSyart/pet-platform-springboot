package com.pets.pojo.vo;

import com.pets.pojo.entity.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetOrderVO {

    private Long id;

    private String order_id;

    private String customerName;

    private Double totalAmount;

    private String note;

    private Integer orderStatus;

    private Integer order_type;

    private String createdate;

    private String updatedate;

    private Integer status;

    private Integer pickMethod;

    private List<OrderInfo> orderInfoList;

}
