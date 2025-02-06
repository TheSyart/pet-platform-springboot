package com.pets.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {

    private String jwt;

    private String id;

    private String name;

    private String username;

    private Integer job;

    private List<String> permission;

}
