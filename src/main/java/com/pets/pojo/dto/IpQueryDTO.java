package com.pets.pojo.dto;

import lombok.Data;

@Data
public class IpQueryDTO {

    private Integer page;

    private Integer pageSize;

    private String begin;

    private String end;

    private String username;

    private String ip;

    private Integer accountType;

    private Integer result;

    private Integer loginType;

}
