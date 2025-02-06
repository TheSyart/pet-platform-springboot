package com.pets.mapper;

import com.pets.pojo.dto.CalendarDTO;
import com.pets.pojo.vo.CalendarVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CalendarMapper {

    List<CalendarVO> getEventByParentId(String parent_id);

    int insertNewEvent(CalendarDTO calendarDTO);

    int updateEvent(CalendarDTO calendarDTO);

    int deleteEvent(CalendarDTO calendarDTO);

    String getCalendarIdByCreateDateAndId(CalendarDTO calendarDTO);
}
