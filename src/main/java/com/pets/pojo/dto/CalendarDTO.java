package com.pets.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDTO {
    private String id;

    private String parent_id;

    private String username;

    private String createDate;

    private String event;
}
