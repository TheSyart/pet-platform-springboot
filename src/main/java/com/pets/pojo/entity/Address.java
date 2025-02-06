package com.pets.pojo.entity;

import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class Address {

    private String id;

    private String username;

    private Long customer_id;  // 用户ID

    @NotNull(message = "收件人姓名不能为空")
    @Size(min = 1, max = 10, message = "收件人姓名长度应在1到10个字符之间")
    private String name;  // 收件人姓名

    @NotNull(message = "联系电话不能为空")
    @Pattern(regexp = "^[0-9]{11}$", message = "联系电话必须是有效的11位数字")
    private String phone;  // 收件人联系电话

    @Min(value = 0, message = "性别必须为0或1")
    @Max(value = 1, message = "性别必须为0或1")
    private Integer gender;  // 收件人性别

    @NotNull(message = "地址名不能为空")
    @Size(min = 1, max = 100, message = "地址名长度应在1到100个字符之间")
    private String addressTitle;  // 地址名

    @NotNull(message = "地址详情不能为空")
    @Size(min = 5, max = 255, message = "地址详情长度应在5到255个字符之间")
    private String addressDetails;  // 地址详情

    private Integer defaultAddress;  // 是否为默认地址

    private Integer status;  // 状态

    @NotNull(message = "经度不能为空")
    private Double longitude;  // 经度

    @NotNull(message = "纬度不能为空")
    private Double latitude;  // 纬度

    @NotNull(message = "门牌号不能为空")
    @Size(min = 1, max = 20, message = "门牌号长度应在1到20个字符之间")
    private String door;  // 门牌号
}
