package com.pets.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarVO {
    private String id;

    private String parent_id;

    private String createDate;

    private String event;
}
