package com.pets.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRecordVO {

    private Integer operatorType;

    private String operatorName;

    private Integer operationContent;

    private String operatorTime;

}
