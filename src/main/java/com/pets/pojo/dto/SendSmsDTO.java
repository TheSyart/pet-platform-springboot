package com.pets.pojo.dto;

import lombok.Data;

@Data
public class SendSmsDTO {

    private String phoneNumber;

    private String sendType;

}
