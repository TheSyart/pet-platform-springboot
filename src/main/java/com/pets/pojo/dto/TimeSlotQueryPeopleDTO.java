package com.pets.pojo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TimeSlotQueryPeopleDTO {

    private LocalDate time;

    private Integer time_id;
}
