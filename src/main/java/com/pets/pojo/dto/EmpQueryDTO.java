package com.pets.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpQueryDTO {
    private Integer page;
    private Integer pageSize;
    private String name;
    private Integer gender;
    private Integer job;
    private Integer status;
    private String begin;
    private String end;
}
