package com.pets.service.impl;

import com.pets.pojo.dto.CalendarDTO;
import com.pets.mapper.CalendarMapper;
import com.pets.mapper.CustomerMapper;
import com.pets.pojo.entity.Customer;
import com.pets.pojo.vo.CalendarVO;
import com.pets.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CalendarServiceImpl implements CalendarService {
    @Autowired
    CalendarMapper calendarMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Override
    public List<CalendarVO> getAllCalendarEvent(CalendarDTO calendarDTO) {
        //获得该username下用户的父级id
        Customer customer = new Customer();
        customer.setUsername(calendarDTO.getUsername());

        return calendarMapper.getEventByParentId(customerMapper.getCustomerInformation(customer).getId());
    }

    @Override
    public String insertNewCalendarEvent(CalendarDTO calendarDTO) {
        if (calendarMapper.insertNewEvent(calendarDTO) == 1){

            return "事件添加成功!" + calendarMapper.getCalendarIdByCreateDateAndId(calendarDTO);
        }else {
            return "事件添加失败!";
        }

    }

    @Override
    public String updateCalendarEvent(CalendarDTO calendarDTO) {
        if (calendarMapper.updateEvent(calendarDTO) == 1){
            return "事件更新成功!";
        }else {
            return "事件更新失败!";
        }
    }

    @Override
    public String deleteCalendarEvent(CalendarDTO calendarDTO) {
        if (calendarMapper.deleteEvent(calendarDTO) == 1){
            return "事件删除成功!";
        }
        return "事件删除失败!";
    }
}
