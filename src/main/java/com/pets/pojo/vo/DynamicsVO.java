package com.pets.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicsVO {

    private String parent_id;

    private String id;

    private String name;

    private String sendtime;

    private String updateDate;

    private Integer likeCount;

    private String content;

    private Boolean isLike;

    private String image_path;

    //多表查询获取该动态用户的头像
    private String image;

    private Integer status;
}
