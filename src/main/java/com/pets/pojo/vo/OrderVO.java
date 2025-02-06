package com.pets.pojo.vo;

import com.pets.pojo.entity.Address;
import com.pets.pojo.entity.OrderInfo;
import com.pets.pojo.entity.OrderRecord;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {

    private String id;

    private String order_id;

    private String customer_id;

    private String customerName;

    private Integer pickMethod;

    private Double totalAmount;

    private Integer paymentMethod;

    private String note;

    private Integer orderstatus;

    private String createdate;

    private Integer order_type;

    private Address address;

    private String reservedName;

    private String reservedPhone;

    private String secretKey;

    private String appointmentTime;
    // 医师信息
    private String doctorName;

    private String doctorPhone;

    // 宠物信息
    private String petName;

    // 服务时段信息
    private String serviceTimeSlot;

    private List<OrderInfo> orderInfoList;

    // 订单日志
    private List<OrderRecordVO> orderRecordList;

}
