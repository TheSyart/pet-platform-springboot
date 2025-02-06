package com.pets.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dynamics {
    private String parent_id;

    private String id;

    private String name;

    private String sendtime;

    private String updateDate;

    private Integer likeCount;

    private String content;

    private String likepeople;

    private String image_path;

}
