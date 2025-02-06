package com.pets.pojo.vo;

import lombok.Data;

@Data
public class HomeLoginVO {

    String loginCategory;

    Integer total;

    Integer success;

    Integer failed;
}
