package com.pets.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpVO {

    private Integer id;

    private String phone;

    private String name;

    private String birth;

    private Integer status;

    private String entrydate;

    private String modifydate;

    private Short gender;

    private Short job;
}
