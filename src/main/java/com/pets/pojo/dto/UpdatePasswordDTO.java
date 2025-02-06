package com.pets.pojo.dto;

import lombok.Data;

@Data
public class UpdatePasswordDTO {

    private String id;

    private Integer stepId;

    private String password;

}
