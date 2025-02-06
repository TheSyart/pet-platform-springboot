package com.pets.pojo.dto;

import com.pets.pojo.entity.OrderInfo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetOrderDTO {

    private String order_id;

    private String shopping_id;

    private String secretKey;

    private String customer_id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotNull(message = "提货方式不能为空")
    private Integer pickMethod;

    @NotNull(message = "总金额不能为空")
    @DecimalMin(value = "0.0", inclusive = false, message = "总金额必须大于0")
    private Double totalAmount;

    @NotNull(message = "支付方式不能为空")
    private Integer paymentMethod;

    private String addressId;

    @NotEmpty(message = "订单信息列表不能为空")
    private List<OrderInfo> orderInfoList;

    @NotNull(message = "订单类型不能为空")
    private Integer order_type;

    private String reservedName;

    private String reservedPhone;

    private String note;

    private Integer orderstatus;

    //门店服务:预约服务的宠物id
    private Long appointmentPet;

    //门店服务:预约时间
    private LocalDate appointmentTime;

    //门店服务: 预约的护理医师id
    private Long appointmentDoctor;

    //门店服务:预约所属时段编号
    private Integer timeslot_id;

    private Integer status;
}
