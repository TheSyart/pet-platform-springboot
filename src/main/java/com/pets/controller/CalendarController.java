package com.pets.controller;

import com.pets.pojo.dto.CalendarDTO;
import com.pets.service.CalendarService;
import com.pets.utils.base.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin
@RequestMapping("/petCalendar")
@RestController
public class CalendarController {
    @Autowired
    CalendarService calendarService;

    //获取该用户宠物日历全部事件
    @PostMapping("/getAllEvent")
    public ResponseData calendarList(@RequestBody CalendarDTO calendarDTO) {
        System.out.println("获取该用户宠物日历全部事件: " + calendarDTO);
        return ResponseData.OK(calendarService.getAllCalendarEvent(calendarDTO));
    }
    //添加该用户宠物日历新事件
    @PostMapping("/InsertNewEvent")
    public ResponseData calendarInsert(@RequestBody CalendarDTO calendarDTO) {
        return ResponseData.OK(calendarService.insertNewCalendarEvent(calendarDTO));
    }
    //更新该用户宠物日历事件
    @PostMapping("/updateEvent")
    public ResponseData calendarUpdate(@RequestBody CalendarDTO calendarDTO) {
        return ResponseData.OK(calendarService.updateCalendarEvent(calendarDTO));
    }
    //删除该用户宠物日历一个事件
    @PostMapping("/deleteEvent")
    public ResponseData calendarDelete(@RequestBody CalendarDTO calendarDTO) {
        return ResponseData.OK(calendarService.deleteCalendarEvent(calendarDTO));
    }
}
