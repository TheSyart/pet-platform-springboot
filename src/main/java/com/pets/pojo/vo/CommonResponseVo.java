package com.pets.pojo.vo;

import lombok.Data;
import java.util.List;

@Data
public class CommonResponseVo<T>{
    private List<T> list;
    private Long total;
}
