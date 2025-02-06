package com.pets.service;

import com.pets.pojo.dto.CalendarDTO;
import com.pets.pojo.vo.CalendarVO;

import java.util.List;

public interface CalendarService{
    List<CalendarVO> getAllCalendarEvent(CalendarDTO calendarDTO);

    String insertNewCalendarEvent(CalendarDTO calendarDTO);

    String updateCalendarEvent(CalendarDTO calendarDTO);

    String deleteCalendarEvent(CalendarDTO calendarDTO);
}
