package com.pets.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotVO {

    private Integer id;

    private Integer timeslot_id;

    private String time_slot;

    private String start_time;

    private String end_time;

    private Integer capacity;

    //该时段当前已预约人数
    private Integer nowPeople;

}
