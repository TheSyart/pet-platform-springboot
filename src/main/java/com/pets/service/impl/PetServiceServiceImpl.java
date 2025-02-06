package com.pets.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pets.mapper.PetServiceMapper;
import com.pets.pojo.dto.*;
import com.pets.pojo.entity.PetService;
import com.pets.pojo.vo.*;
import com.pets.service.PetServiceService;
import com.pets.utils.base.BaseErrorException;
import com.pets.utils.pic.HandlePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PetServiceServiceImpl implements PetServiceService {

    @Autowired
    PetServiceMapper petServiceMapper;

    @Autowired
    HandlePic handlePic;

    @Override
    public List<ServiceTypeVO> queryAllPetServiceType() throws UnknownHostException {
        List<ServiceTypeVO> serviceTypeVOList = petServiceMapper.queryAllPetServiceType();
        for (ServiceTypeVO serviceTypeVO : serviceTypeVOList) {
            serviceTypeVO.setImage(handlePic.fixPath(serviceTypeVO.getImage()));
        }
        return serviceTypeVOList;
    }

    @Override
    public List<PetService> queryPetServiceById(Long id) throws UnknownHostException {
        List<PetService> petServiceList = petServiceMapper.queryPetServiceById(id);
        for (PetService petService : petServiceList) {
            petService.setImage(handlePic.fixPath(petService.getImage()));
        }
        return petServiceList;
    }

    @Override
    public List<TimeSlotVO> queryTimeSlot(TimeSlotDTO timeSlotDTO) {
        List<TimeSlotQueryPeopleDTO> timeSlotQueryPeopleDTOS = new ArrayList<>();

        List<TimeSlotVO> timeSlotVOS = petServiceMapper.queryTimeSlot();


        for (TimeSlotVO timeSlotVO : timeSlotVOS) {
            TimeSlotQueryPeopleDTO timeSlotQueryPeopleDTO = new TimeSlotQueryPeopleDTO();

            timeSlotQueryPeopleDTO.setTime(timeSlotDTO.getToday());
            timeSlotQueryPeopleDTO.setTime_id(timeSlotVO.getTimeslot_id());

            timeSlotQueryPeopleDTOS.add(timeSlotQueryPeopleDTO);
        }

        List<Integer> peopleList = petServiceMapper.queryTimeSlotPeople(timeSlotQueryPeopleDTOS, timeSlotDTO.getDoctorId());

        for (int i = 0; i < timeSlotVOS.size(); i++) {
            timeSlotVOS.get(i).setNowPeople(peopleList.get(i));
        }

        return timeSlotVOS;
    }

    @Override
    public CommonResponseVo queryAllService(PageRequestDto<ServiceQueryDTO> pageRequestDto) {
        PageHelper.startPage(pageRequestDto.getPagination().getPage(), pageRequestDto.getPagination().getSize());

        ServiceQueryDTO serviceQueryDTO = pageRequestDto.getConditions();
        CommonResponseVo commonResponseVo = new CommonResponseVo();

        List<PetServiceV0> petServiceV0s = petServiceMapper.queryAllService(serviceQueryDTO);
        PageInfo<PetServiceV0> pageInfo = new PageInfo<>(petServiceV0s);

        commonResponseVo.setList(pageInfo.getList());
        commonResponseVo.setTotal(pageInfo.getTotal());
        return commonResponseVo;
    }

    @Override
    public PetService queryOneService(Long id) throws UnknownHostException {
        PetService petService = petServiceMapper.queryOneService(id);
        petService.setImage(handlePic.fixPath(petService.getImage()));
        return petService;
    }

    @Override
    public String insertNewService(PetServiceCreateInfo petServiceCreateInfo) {
        if (petServiceMapper.insertNewService(petServiceCreateInfo) != 1){
            throw new BaseErrorException("服务录入失败!");
        }
        return "商品录入成功!";
    }

    @Override
    public List<String> allServiceType() {
        return petServiceMapper.allServiceType();
    }

    @Override
    public String updateServiceStatus(UpdateStatusDTO updateStatusDTO) {
        if (petServiceMapper.updateServiceStatus(updateStatusDTO.getId(), updateStatusDTO.getStatus()) != 1){
            throw new BaseErrorException("服务状态操作失败!");
        }
        return "服务状态更改成功!";
    }

    @Override
    public String updateService(PetService petService) {
        if (petServiceMapper.updateService(petService) != 1){
            throw new BaseErrorException("服务信息编辑失败!");
        }
        return "服务信息更改成功!";
    }

    @Override
    public String deleteServiceImage(String id) {
        if(petServiceMapper.deleteServiceImage(id) != 1){
            throw new BaseErrorException("数据库同步删除照片失败!");
        }
        return null;
    }

}
