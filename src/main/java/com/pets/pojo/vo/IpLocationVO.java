package com.pets.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IpLocationVO {

    private Long id;

    private String account;        // 登录用户 ID

    private Integer accountType;   // 登录用户 类型

    private String country;        // 国家

    private String region;         // 地区（省）

    private String city;           // 城市

    private Double lat;            // 纬度

    private Double lon;            // 经度

    private String isp;            // 互联网服务提供商

    private String ip;             // 查询的 IP 地址

    private String message;        // 错误原因

    private Integer result;        // 登陆结果

    private Integer login_type;    // 登陆方式

    private LocalDateTime createdDate;
}
