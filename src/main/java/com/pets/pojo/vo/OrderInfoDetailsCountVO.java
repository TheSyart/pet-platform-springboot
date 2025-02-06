package com.pets.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderInfoDetailsCountVO {

    private Integer orderType;

    private List<CommonOrderVO> orderInfoList;


}
