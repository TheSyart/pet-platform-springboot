package com.pets.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVO {

    private String id;

    private String name;

    private String birth;

    private Integer status;

    private String createdate;

    private String modifydate;

    private String phone;

    private Integer gender;
}
